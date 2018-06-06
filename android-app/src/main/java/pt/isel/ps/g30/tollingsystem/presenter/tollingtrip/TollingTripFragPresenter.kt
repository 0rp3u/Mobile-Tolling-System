package pt.isel.ps.g30.tollingsystem.presenter.tollingtrip

import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragmentView

interface TollingTripFragPresenter : BasePresenter<TollingTripsFragmentView> {

    fun getTollingTripList(vehicleId: Int)

}