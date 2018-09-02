package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehiclesFragmentView

class VehiclesFragPresenterImpl(private val interactor: VehicleInteractor) :
        BasePresenterImpl<VehiclesFragmentView>(), VehiclesFragPresenter{

    private val jobs = Job()


    override fun getVehicleList() {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val vehicleList = interactor.getVehicleListLiveData().await()

                view?.showVehicleList(vehicleList)
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