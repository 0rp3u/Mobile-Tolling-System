package pt.isel.ps.g30.tollingsystem.view.tollingtrip

import android.view.View
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface TollingTripsFragmentView : BaseView {

    fun showTripList(list: List<TollingTrip>) //
    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}