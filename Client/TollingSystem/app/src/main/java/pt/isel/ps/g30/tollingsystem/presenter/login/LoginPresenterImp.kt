package pt.isel.ps.g30.tollingsystem.presenter.login

import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI
import pt.isel.ps.g30.tollingsystem.view.login.LoginView
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenterImpl

class LoginPresenterImp(private val interactor: AuthInteractor) :
        BasePresenterImpl<LoginView>(), LoginPresenter{

    private lateinit var fetchJob : Job //TODO this might not be right, have to verify

    override fun authenticate(login: String, password: String) {
        fetchJob = launch (UI) {
            view?.apply {
                showLoadingIndicator()
                try {
                   interactor.authenticate(login, password).await()
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