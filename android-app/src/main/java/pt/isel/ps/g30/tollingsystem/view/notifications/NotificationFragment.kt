package pt.isel.ps.g30.tollingsystem.view.notifications

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.notifications_fragment.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.injection.module.NotificationModule
import pt.isel.ps.g30.tollingsystem.model.Notification
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseFragment
import javax.inject.Inject

class NotificationFragment: BaseFragment<NotificationPresenter, NotificationView>(), NotificationView{

    lateinit var notificationRecyclerViewAdapter: NotificationRecyclerViewAdapter

    @Inject
    override lateinit var presenter: NotificationPresenter

    override fun injectDependencies() {
       app.applicationComponent
                .plus(NotificationModule())
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
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingIndicator() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showDoneMessage() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
