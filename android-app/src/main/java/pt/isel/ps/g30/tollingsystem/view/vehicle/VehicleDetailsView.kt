package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.view.View
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.view.base.BaseView


interface VehicleDetailsView : BaseView {

    fun showVehicleBasicInfo(vehicle: Vehicle)
    fun showVehicleTripNumber(tripNumber: Int)
    fun showVehiclePaidAmount(amount: Double)
    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}