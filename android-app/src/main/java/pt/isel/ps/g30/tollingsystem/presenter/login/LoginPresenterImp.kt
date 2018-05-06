package pt.isel.ps.g30.tollingsystem.presenter.login

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import pt.isel.ps.g30.tollingsystem.view.login.LoginView
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl

class LoginPresenterImp(private val interactor: AuthInteractor, private val sharedPreferences: SharedPreferences) :
        BasePresenterImpl<LoginView>(), LoginPresenter{

    private lateinit var fetchJob : Job //TODO this might not be right, have to verify

    override fun authenticate(login: String, password: String) {
        fetchJob = launch (UI) {
            view?.apply {
                showLoadingIndicator()
                try {
                    interactor.authenticate(login, password).await()
                    sharedPreferences.edit {//TODO might not be right to do here?
                        putString("login", login)
                        putString("password", password)
                    }
                    hideLoadingIndicator()
                    successfullLogin()

                }catch (e: Throwable){
                    hideLoadingIndicator()
                    failedLogin()
                }
            }
        }
    }

    override fun cancelRequest(){
        fetchJob.cancel()
    }
}