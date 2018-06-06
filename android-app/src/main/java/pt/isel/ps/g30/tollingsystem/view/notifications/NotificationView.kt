package pt.isel.ps.g30.tollingsystem.view.notifications

import android.view.View
import pt.isel.ps.g30.tollingsystem.data.api.model.Notification
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface NotificationView : BaseView {

    fun showNotificationList(list: List<Notification>)
    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}