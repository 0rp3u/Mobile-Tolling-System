package pt.isel.ps.g30.tollingsystem.view.notifications

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.support.v4.startActivity
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.extensions.app
import pt.isel.ps.g30.tollingsystem.databinding.NotificationsFragmentBinding
import pt.isel.ps.g30.tollingsystem.injection.module.NotificationModule
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.model.Notification
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenter
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenterImp
import pt.isel.ps.g30.tollingsystem.view.base.BaseFragment
import javax.inject.Inject

class NotificationFragment: BaseFragment<NotificationPresenter, NotificationView>(), NotificationView{



    lateinit var notificationRecyclerViewAdapter: NotificationRecyclerViewAdapter
    private lateinit var bind: NotificationsFragmentBinding

    @Inject
    lateinit var interactor: NotificationInteractor

    override fun injectDependencies() {
       app.applicationComponent
                .plus(NotificationModule())
                .injectTo(this)
    }

    override fun instantiatePresenter(): NotificationPresenter
            = NotificationPresenterImp(interactor)





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.notifications_fragment, container, false)

        bind = DataBindingUtil.bind<ViewDataBinding>(view) as NotificationsFragmentBinding

        notificationRecyclerViewAdapter = NotificationRecyclerViewAdapter { (id, description) ->

        }

        bind.recyclerView.apply{
            adapter = notificationRecyclerViewAdapter
            layoutManager = LinearLayoutManager(activity)

        }
        presenter.getNotificationList()

        return view
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
