package pt.isel.ps.g30.tollingsystem.presenter.login

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.login.LoginView

interface LoginPresenter : BasePresenter<LoginView> {

    fun authenticate(login:String, password:String)
}