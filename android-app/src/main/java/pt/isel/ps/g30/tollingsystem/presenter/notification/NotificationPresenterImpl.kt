package pt.isel.ps.g30.tollingsystem.presenter.notification

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.data.api.model.Notification
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationView

class NotificationPresenterImpl(private val interactor: NotificationInteractor) :
        BasePresenterImpl<NotificationView>(), NotificationPresenter{

    private var notificationList = listOf<Notification>()
    private val jobs = Job()

    override fun getNotificationList() {
        launch (UI, parent = jobs) {
            view?.showLoadingIndicator()
            try {
                notificationList = interactor.getNotificationList().await()
                delay(1000)
                view?.showNotificationList(notificationList)
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