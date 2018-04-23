package pt.isel.ps.g30.tollingsystem.view.vehicle

import pt.isel.ps.g30.tollingsystem.model.Vehicle
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface VehicleView : BaseView {

    fun showVehicleList(list: List<Vehicle>)
    fun showDoneMessage()
    fun showErrorMessage()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}