package pt.isel.ps.g30.tollingsystem.presenter.notification

import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationView

interface NotificationPresenter : BasePresenter<NotificationView> {

    fun getNotificationList()

    fun dismissNotification(notification: Notification)

    fun cancelTrip(notification: Notification)

    fun confirmTrip(notification: Notification)

    fun disputePaidTrip(notification: Notification)

}