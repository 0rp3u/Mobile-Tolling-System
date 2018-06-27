package pt.isel.ps.g30.tollingsystem.presenter.navigation

import pt.isel.ps.g30.tollingsystem.data.database.model.ActiveTrip
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationFragmentView
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragmentView

interface NavigationFragPresenter : BasePresenter<NavigationFragmentView> {

    fun setUpMap()

    fun prepareVehiclesDialog()

    fun prepareCancelActiveTripDialog(activeTrip: ActiveTrip)

    fun setActiveVehicle(vehicle: Vehicle)

    fun removeActiveVehicle(vehicle: Vehicle)

    fun startTrip(tollingPlaza: TollingPlaza)

    fun finishTrip(tollingPlaza: TollingPlaza)

    fun cancelActiveTrip(trip: ActiveTrip)

}