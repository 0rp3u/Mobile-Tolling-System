package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivityView

class VehicleActivityPresenterImpl() : BasePresenterImpl<VehicleActivityView>(), VehicleActivityPresenter{

    private val jobs = Job()

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}