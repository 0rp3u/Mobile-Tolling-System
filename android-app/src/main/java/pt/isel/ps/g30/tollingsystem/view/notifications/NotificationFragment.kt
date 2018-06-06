package pt.isel.ps.g30.tollingsystem.view.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notifications_fragment.*
import kotlinx.android.synthetic.main.progress_bar.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.data.api.model.Notification
import pt.isel.ps.g30.tollingsystem.extensions.longSnackbar
import pt.isel.ps.g30.tollingsystem.extensions.snackbar
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseFragment
import javax.inject.Inject

class NotificationFragment: BaseFragment<NotificationPresenter, NotificationView>(), NotificationView{

    lateinit var notificationRecyclerViewAdapter: NotificationRecyclerViewAdapter

    @Inject
    override lateinit var presenter: NotificationPresenter

    override fun injectDependencies() {
       app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.notifications_fragment, container, false)

        notificationRecyclerViewAdapter = NotificationRecyclerViewAdapter { (id, description) ->

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.apply{
            adapter = notificationRecyclerViewAdapter
            layoutManager = LinearLayoutManager(activity)

        }
        presenter.getNotificationList()

    }


    override fun showNotificationList(list: List<Notification>) {
        notificationRecyclerViewAdapter.notificationList = list
    }


    override fun showLoadingIndicator() {

        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }



    override fun showDoneMessage(message:String?) {
        snackbar(this.view!!, message ?: "done")
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
       if(action != null)
           longSnackbar(this.view!!, error ?: "error, something when wrong","repeat?", action)
       else
           snackbar(this.view!!, error ?: "error, something when wrong")
    }

    override fun onDestroyView() {
        presenter.cancelRequest()
        super.onDestroyView()
    }

}
