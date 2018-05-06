package pt.isel.ps.g30.tollingsystem.view.notifications

import pt.isel.ps.g30.tollingsystem.model.Notification
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface NotificationView : BaseView {

    fun showNotificationList(list: List<Notification>)
    fun showDoneMessage()
    fun showErrorMessage()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}