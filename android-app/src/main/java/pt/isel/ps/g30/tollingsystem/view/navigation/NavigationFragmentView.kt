package pt.isel.ps.g30.tollingsystem.view.navigation

import android.view.View
import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface NavigationFragmentView : BaseView{

    fun showPlazaLocations(list: List<TollingPlaza>)
    fun showVehiclesDialog(list: List<Vehicle>)
    fun showCancelActiveTripDialog(trip: CurrentTransaction)
    fun showActiveTrip(trip: CurrentTransaction)
    fun showActiveVehicle(vehicle: Vehicle?)
    fun removeActiveVehicle()
    fun removeActiveTrip(trip: CurrentTransaction)

    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}