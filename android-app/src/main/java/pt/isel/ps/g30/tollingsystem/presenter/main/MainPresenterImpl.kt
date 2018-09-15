package pt.isel.ps.g30.tollingsystem.presenter.main

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.view.login.LoginView
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.main.MainView

class MainPresenterImpl(
        authInteractor: AuthInteractor,
        private val interactor: NotificationInteractor
) : BasePresenterImpl<MainView>(authInteractor), MainPresenter{

    private val jobs = Job()

    override fun setNotificationNumber() {
        launch (UI, parent =jobs) {
            view?.showLoadingIndicator()
            try {
                val notificationsLiveData = interactor.getNotificationListLiveData().await()

                view?.showNotificationNumber(notificationsLiveData)
                view?.hideLoadingIndicator()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}