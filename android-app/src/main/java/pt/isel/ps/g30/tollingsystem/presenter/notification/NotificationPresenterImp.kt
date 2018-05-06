package pt.isel.ps.g30.tollingsystem.presenter.notification

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.model.Notification
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationView

class NotificationPresenterImp(private val interactor: NotificationInteractor) :
        BasePresenterImpl<NotificationView>(), NotificationPresenter{

    private var notificationList = listOf<Notification>()
    private lateinit var fetchJob : Job //TODO this might not be right, have to verify


    override fun getNotificationList() {
        fetchJob = launch (UI) {
            view?.apply {
                showLoadingIndicator()
                try {
                    notificationList = interactor.getNotificationList().await()
                    showNotificationList(notificationList)
                    hideLoadingIndicator()
                    showDoneMessage()

                }catch (e: Throwable){
                    hideLoadingIndicator()
                    showErrorMessage()
                }

            }
        }
    }

    override fun cancelRequest(){
        fetchJob.cancel()
    }
}