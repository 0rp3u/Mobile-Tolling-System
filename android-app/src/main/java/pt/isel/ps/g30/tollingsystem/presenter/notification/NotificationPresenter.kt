package pt.isel.ps.g30.tollingsystem.presenter.notification

import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationView

interface NotificationPresenter : BasePresenter<NotificationView> {

    fun getNotificationList()

    fun dismissNotification(notification: Notification)

    fun cancelTransaction(notification: Notification)

    fun confirmTransaction(notification: Notification)

    fun disputePaidTransaction(notification: Notification)

}