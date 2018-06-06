package pt.isel.ps.g30.tollingsystem.presenter.splash

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.splash.SplashView

interface SplashPresenter : BasePresenter<SplashView> {

    fun authenticate(login:String, password:String)
}