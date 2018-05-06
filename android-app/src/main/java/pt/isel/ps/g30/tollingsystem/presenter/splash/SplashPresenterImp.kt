package pt.isel.ps.g30.tollingsystem.presenter.login

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.view.login.LoginView
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.splash.SplashView

class SplashPresenterImp(private val interactor: AuthInteractor, private val sharedPreferences: SharedPreferences) :
        BasePresenterImpl<SplashView>(), SplashPresenter{

    private lateinit var fetchJob : Job
    override fun authenticate(login: String, password: String) {
        fetchJob = launch (UI) {
            view?.apply {
                showLoadingIndicator()
                try {
                    delay(5000)
                    interactor.authenticate(login, password).await()
                    sharedPreferences.edit {
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