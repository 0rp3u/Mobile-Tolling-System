package pt.isel.ps.g30.tollingsystem.view.tollingTripInfo


import android.view.View
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface TollingTripInfoView : BaseView {

    fun showTripInfo(tripInfo: TollingTrip)
    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
}