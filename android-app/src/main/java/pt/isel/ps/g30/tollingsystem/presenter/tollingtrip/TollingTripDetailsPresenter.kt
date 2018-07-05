package pt.isel.ps.g30.tollingsystem.presenter.tollingtrip

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripDetailsView

interface TollingTripDetailsPresenter : BasePresenter<TollingTripDetailsView> {

    fun getTollingTrip(tripId: Int)

}