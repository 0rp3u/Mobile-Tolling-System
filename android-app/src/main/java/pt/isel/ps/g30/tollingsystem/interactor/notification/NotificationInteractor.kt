package pt.isel.ps.g30.tollingsystem.interactor.notification

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.interactor.base.BaseInteractor
import pt.isel.ps.g30.tollingsystem.model.Notification

interface NotificationInteractor : BaseInteractor {

    suspend fun getNotificationList() : Deferred<List<Notification>>

}
