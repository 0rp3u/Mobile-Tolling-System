package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTransactionInteractor
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleDetailsView

class
VehicleDetailsPresenterImpl(private val vehicleInteractor: VehicleInteractor, private val transactionInteractor: TollingTransactionInteractor) :
        BasePresenterImpl<VehicleDetailsView>(), VehicleDetailsPresenter{

    private val jobs = Job()

    override fun getVehicleDetails(id: Int) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val vehicle = vehicleInteractor.getVehicle(id)
                val trips = transactionInteractor.getVehicleTransactionList(id).await()
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