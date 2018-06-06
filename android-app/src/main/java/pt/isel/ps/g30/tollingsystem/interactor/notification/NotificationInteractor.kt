package pt.isel.ps.g30.tollingsystem.interactor.notification

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor
import pt.isel.ps.g30.tollingsystem.data.api.model.Notification

interface NotificationInteractor : BaseInteractor {

    suspend fun getNotificationList() : Deferred<List<Notification>>

}
