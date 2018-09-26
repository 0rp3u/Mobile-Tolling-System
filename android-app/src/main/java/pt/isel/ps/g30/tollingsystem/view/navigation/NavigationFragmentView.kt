package pt.isel.ps.g30.tollingsystem.view.navigation

import android.view.View
import pt.isel.ps.g30.tollingsystem.data.database.model.UnvalidatedTransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface NavigationFragmentView : BaseView{

    fun showPlazaLocations(list: List<TollingPlaza>)
    fun showVehiclesDialog(list: List<Vehicle>)
    fun showCancelActiveTransactionDialog(transaction: UnvalidatedTransactionInfo)
    fun showActiveTransaction(transaction: UnvalidatedTransactionInfo)
    fun showActiveVehicle(vehicle: Vehicle?)
    fun removeActiveVehicle()
    fun removeActiveTransaction()
    fun setCurrentTransaction(transaction: UnvalidatedTransactionInfo)

    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}