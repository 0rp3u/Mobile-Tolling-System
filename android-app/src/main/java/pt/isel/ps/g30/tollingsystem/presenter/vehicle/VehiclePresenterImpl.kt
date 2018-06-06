package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleView

class
VehiclePresenterImpl(private val interactor: VehicleInteractor) :
        BasePresenterImpl<VehicleView>(), VehiclePresenter{

    private lateinit var vehicle: Vehicle
    private val jobs = Job()

    override fun getVehicle(id: Int) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                vehicle = interactor.getVehicle(id).await()
                delay(1000)
                view?.showVehicle(vehicle)
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