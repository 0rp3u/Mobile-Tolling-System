package pt.isel.ps.g30.tollingsystem.presenter.vehicle

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleDetailsView

interface VehicleDetailsPresenter : BasePresenter<VehicleDetailsView> {

    fun getVehicleDetails(id:Int)

}