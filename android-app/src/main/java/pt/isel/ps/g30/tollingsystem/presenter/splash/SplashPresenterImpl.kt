package pt.isel.ps.g30.tollingsystem.presenter.splash

import android.content.SharedPreferences
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.splash.SplashView

class SplashPresenterImpl(private val interactor: AuthInteractor, private val sharedPreferences: SharedPreferences) :
        BasePresenterImpl<SplashView>(), SplashPresenter{

    private val jobs = Job()

    override fun authenticate(login: String, password: String) {
        launch (UI, parent = jobs) {

            try {
                interactor.authenticate(login, password).await()
//                    sharedPreferences.edit {
//                        putString("login", login)
//                        putString("password", password)
//                    }
                view?.successfullLogin()

            }catch (e: Throwable){
                view?.failedLogin()
            }
        }
    }

    override fun cancelRequest(){
        jobs.cancelChildren()
    }
}