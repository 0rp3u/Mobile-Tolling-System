package pt.isel.ps.g30.tollingsystem.interactor.notification

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import pt.isel.ps.g30.tollingsystem.data.database.model.ActiveTrip
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip

interface NotificationInteractor : BaseInteractor {

    suspend fun getNotificationList() : Deferred<List<Notification>>

    suspend fun getNotificationListLiveData() : Deferred<LiveData<List<Notification>>>

    suspend fun confirmTrip(notification: Notification) : Job

    suspend fun cancelTrip(notification: Notification) : Job

    suspend fun dismissNotification(notification: Notification): Job

    fun sendStartTripNotification(trip: ActiveTrip)

    fun sendFinishTripNotification(trip: TollingTrip)
}
