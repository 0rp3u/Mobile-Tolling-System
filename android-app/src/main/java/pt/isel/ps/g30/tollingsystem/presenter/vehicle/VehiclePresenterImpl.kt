package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractor
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleDetailsView
import kotlin.math.roundToLong

class
VehiclePresenterImpl(private val vehicleInteractor: VehicleInteractor, private val tripInteractor: TollingTripInteractor) :
        BasePresenterImpl<VehicleDetailsView>(), VehiclePresenter{

    private val jobs = Job()

    override fun getVehicleDetails(id: Int) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val vehicle = vehicleInteractor.getVehicle(id)
                val trips = tripInteractor.getVehicleTripList(id).await()
                view?.showVehicleBasicInfo(vehicle.await())
                view?.showVehiclePaidAmount(trips.fold(0.0) { curr, trip -> curr+(trip.paid ?: 0.0)})
                view?.showVehicleTripNumber(trips.size)

                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage()
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}