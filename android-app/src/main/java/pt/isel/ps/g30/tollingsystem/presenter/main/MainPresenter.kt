package pt.isel.ps.g30.tollingsystem.presenter.main

import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter
import pt.isel.ps.g30.tollingsystem.view.main.MainView

interface MainPresenter : BasePresenter<MainView>{

    fun setNotificationNumber()

}

