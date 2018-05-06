package pt.isel.ps.g30.tollingsystem.presenter.notification

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationView

interface NotificationPresenter : BasePresenter<NotificationView> {

    fun getNotificationList()

}