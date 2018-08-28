package pt.isel.ps.g30.tollingsystem.presenter.login

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.view.login.LoginView
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl

class LoginPresenterImpl(private val interactor: AuthInteractor) :
        BasePresenterImpl<LoginView>(), LoginPresenter{

    private val jobs = Job()

    override fun authenticate(login: String, password: String) {
        launch (UI, parent =jobs) {
            view?.showLoadingIndicator()
            try {
                interactor.authenticate(login, password).await()

                view?.hideLoadingIndicator()
                view?.successfullLogin()

            }catch (e: Throwable){
                view?.hideLoadingIndicator()
                view?.failedLogin()
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}