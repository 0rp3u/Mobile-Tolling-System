package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleView

class VehiclePresenterImpl(private val interactor: VehicleInteractor) :
        BasePresenterImpl<VehicleView>(), VehiclePresenter{

    private var vehicleList = listOf<Vehicle>()
    private lateinit var fetchJob : Job //TODO this might not be right, have to verify


    override fun getVehicleList() {
        fetchJob = launch (UI) {
            view?.apply {
                showLoadingIndicator()
                try {
                    vehicleList = interactor.getVehicleList().await()
                    showVehicleList(vehicleList)
                    hideLoadingIndicator()
                    showDoneMessage()

                }catch (e: Throwable){
                    hideLoadingIndicator()
                    showErrorMessage()
                }

            }
        }
    }

    override fun cancelRequest(){
        fetchJob.cancel()
    }
}