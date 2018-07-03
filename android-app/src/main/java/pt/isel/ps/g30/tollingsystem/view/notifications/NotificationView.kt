package pt.isel.ps.g30.tollingsystem.view.notifications

import android.view.View
import androidx.lifecycle.LiveData
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface NotificationView : BaseView {

    fun showNotificationList(liveData: LiveData<List<Notification>>)
    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}