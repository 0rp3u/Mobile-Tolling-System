package pt.isel.ps.g30.tollingsystem.interactor.notification
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.Notification

class NotificationInteractorImpl(private val tollingService: TollingService) : NotificationInteractor {

    override  suspend fun getNotificationList() : Deferred<List<Notification>>{
        val deferred = CompletableDeferred<List<Notification>>()

        deferred.complete(
        listOf(
                Notification(1, "pay debts"),
                Notification(2, "trip detected")
        ))

        return deferred
    }
}
