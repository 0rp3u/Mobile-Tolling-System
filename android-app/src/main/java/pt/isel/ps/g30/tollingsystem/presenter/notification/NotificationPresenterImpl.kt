package pt.isel.ps.g30.tollingsystem.presenter.notification

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationView

class NotificationPresenterImpl(private val interactor: NotificationInteractor) :
        BasePresenterImpl<NotificationView>(), NotificationPresenter{

    private val jobs = Job()

    override fun getNotificationList() {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                val notificationList = interactor.getNotificationListLiveData().await()
                delay(1000)
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