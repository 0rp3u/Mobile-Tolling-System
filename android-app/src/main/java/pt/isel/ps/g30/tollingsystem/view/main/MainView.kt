package pt.isel.ps.g30.tollingsystem.view.main

import androidx.lifecycle.LiveData
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface MainView : BaseView {

    fun showNotificationNumber(notifications: LiveData<List<Notification>>)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()
}