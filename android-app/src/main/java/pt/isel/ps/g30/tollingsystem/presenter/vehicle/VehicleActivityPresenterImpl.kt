package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivityView

class VehicleActivityPresenterImpl(
authInteractor: AuthInteractor
) :  BasePresenterImpl<VehicleActivityView>(authInteractor), VehicleActivityPresenter{

    private val jobs = Job()

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}