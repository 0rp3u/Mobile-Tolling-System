package pt.isel.ps.g30.tollingsystem.presenter.splash

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelChildren
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl
import pt.isel.ps.g30.tollingsystem.view.splash.SplashView

class SplashPresenterImpl(private val interactor: AuthInteractor) :
        BasePresenterImpl<SplashView>(), SplashPresenter{

    private val jobs = Job()

    override fun verifyAuthentication() {
        launch (UI, parent = jobs) {

            try {
                val user = interactor.verifyAuthentication().await()

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