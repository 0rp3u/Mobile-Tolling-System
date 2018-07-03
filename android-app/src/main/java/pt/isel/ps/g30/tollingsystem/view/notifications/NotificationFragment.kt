package pt.isel.ps.g30.tollingsystem.view.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_trip_notification.view.*
import kotlinx.android.synthetic.main.dialog_trip_paid_notification.view.paid_amount
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.imageResource
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.data.database.model.NotificationType
import pt.isel.ps.g30.tollingsystem.extension.*
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
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        notificationRecyclerViewAdapter = NotificationRecyclerViewAdapter {
            when(it.type){
                NotificationType.VehicleAddedNotification -> showVehicleAddedDialog(it)
                NotificationType.TripPaidNotification     -> showPaidDialog(it)
                NotificationType.TripNotification         -> showTripDialog(it)
            }
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

    fun showTripDialog(notification: Notification){
        val trip = notification.trip ?: throw Exception("No trip found on the notification")

        val dialogView = layoutInflater.inflate(R.layout.dialog_trip_notification, null).apply {

                tare.imageResource = trip.vehicle.getIconResource() //<- !! fine because the trip is active in here
                plate.text = trip.vehicle.licensePlate
                from.text = trip.origin.name
                date_origin.text = trip.originTimestamp.dateTimeParsed()
                destination.text = trip.destination.name
                date_destination.text = trip.destTimestamp.dateTimeParsed()



        }

        AlertDialog.Builder(this.requireContext())
                .setTitle("Trip detected options")
                .setView(dialogView)
                .setPositiveButton("I Confirm") { dialogInterface, i -> presenter.confirmTrip(notification); dialogInterface.dismiss() }
                .setNegativeButton("I didn't do this trip") { dialogInterface, i ->presenter.cancelTrip(notification); dialogInterface.dismiss() }
                .setCancelable(true)
                .create()
                .show()

    }

    fun showPaidDialog(notification: Notification){
        val trip = notification.trip ?: throw Exception("No trip found on the notification")

        val dialogView = layoutInflater.inflate(R.layout.dialog_trip_paid_notification, null).apply {


                tare.imageResource = trip.vehicle.getIconResource() //<- !! fine because the trip is active in here
                plate.text = trip.vehicle.licensePlate
                from.text = trip.origin.name
                date_origin.text = trip.originTimestamp.dateTimeParsed()
                destination.text = trip.destination.name
                date_destination.text = trip.destTimestamp.dateTimeParsed()
                paid_amount.text = "${trip?.paid} $"

        }

        AlertDialog.Builder(this.requireContext())
                .setTitle("Paid trip information")
                .setView(dialogView)
                .setPositiveButton("I Confirm") { dialogInterface, i -> presenter.dismissNotification(notification); dialogInterface.dismiss() }
                .setNegativeButton("I didn't do this trip") { dialogInterface, i ->presenter.disputePaidTrip(notification); dialogInterface.dismiss() }
                .setCancelable(true)
                .create()
                .show()


    }

    fun showVehicleAddedDialog(notification: Notification){
        val vehicle = notification.vehicle ?: throw Exception("No vehicle found on the notification")

        val dialogView = layoutInflater.inflate(R.layout.dialog_vehicle_added_notification, null).apply {
            tare.imageResource = vehicle.getIconResource()
            plate.text = vehicle.licensePlate
        }

        AlertDialog.Builder(this.requireContext())
                .setTitle("Vehicle added information")
                .setView(dialogView)
                .setPositiveButton("ok") { dialogInterface, i -> presenter.dismissNotification(notification); dialogInterface.dismiss() }
                .setCancelable(true)
                .create()
                .show()
    }



    override fun showNotificationList(liveData: LiveData<List<Notification>>) {

        liveData.observe(this, Observer { notificationRecyclerViewAdapter.notificationList = it ?: listOf()})

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
