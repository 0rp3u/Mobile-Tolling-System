package pt.isel.ps.g30.tollingsystem.presenter.navigation

import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationFragmentView

interface NavigationFragPresenter : BasePresenter<NavigationFragmentView> {

    fun setUpMap()

    fun prepareVehiclesDialog()

    fun prepareCancelActiveTransactionDialog(currentTransaction: CurrentTransaction)

    fun setActiveVehicle(vehicle: Vehicle)

    fun removeActiveVehicle(vehicle: Vehicle)

    fun startTransaction(tollingPlaza: TollingPlaza)

    fun finishTransaction(tollingPlaza: TollingPlaza)

    fun cancelActiveTransaction(Transaction: CurrentTransaction)

}