package pt.isel.ps.g30.tollingsystem.presenter.notification

import android.util.Log
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationView
import java.lang.Exception

class NotificationPresenterImpl(
        authInteractor: AuthInteractor,
        private val interactor: NotificationInteractor,
        private val SyncronizeInteractor: SynchronizationInteractor
) : BasePresenterImpl<NotificationView>(authInteractor), NotificationPresenter{

    private val jobs = Job()

    override fun getNotificationList() {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                launch {
                    try{
                        SyncronizeInteractor.synchronizeTransactionData()
                    }catch (e:Exception){ Log.d("sync", e.localizedMessage)}
                }.join()

                val notificationList = interactor.getNotificationListLiveData().await()
                view?.showNotificationList(notificationList)
                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage()
            }
        }
    }

    override fun cancelTransaction(notification: Notification) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                interactor.cancelTransaction(notification)

                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage()
            }
        }
    }

    override fun confirmTransaction(notification: Notification) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                interactor.confirmTransaction(notification)

                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage()
            }
        }
    }

    override fun dismissNotification(notification: Notification) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                interactor.dismissNotification(notification)

                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage()
            }
        }
    }

    override fun disputePaidTransaction(notification: Notification) {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                interactor.dismissNotification(notification)

                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.showErrorMessage()
            }
        }
    }

    override fun cancelRequest() {
        jobs.cancelChildren()
    }

}