package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractor
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivityView
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleDetailsView

class VehicleActivityPresenterImpl() : BasePresenterImpl<VehicleActivityView>(), VehicleActivityPresenter{

    private val jobs = Job()

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}