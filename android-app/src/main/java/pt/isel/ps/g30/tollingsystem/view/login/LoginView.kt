package pt.isel.ps.g30.tollingsystem.view.login

import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface LoginView : BaseView {

    fun successfullLogin()
    fun failedLogin()
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}