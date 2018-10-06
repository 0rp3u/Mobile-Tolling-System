package pt.isel.ps.g30.tollingsystem.view.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_notification.view.*
import org.jetbrains.anko.imageResource
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.data.database.model.NotificationType
import pt.isel.ps.g30.tollingsystem.extension.dateTimeParsed
import pt.isel.ps.g30.tollingsystem.extension.getIconResource

class NotificationRecyclerViewAdapter(val listener: (Notification) -> Unit) : RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder>() {

    var notificationList = listOf<Notification>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.bind(notification, listener)
    }

    override fun getItemCount() = notificationList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(notification: Notification, listener: (Notification) -> Unit) {

            when(notification.type){
                NotificationType.VehicleAddedNotification -> showVehicleAddedItemView(itemView, notification)
                NotificationType.TransactionPaidNotification     -> showTransactionPaidItemView(itemView, notification)
                NotificationType.TransactionNotification         -> showTransactionDetectedItemView(itemView, notification)
            }

            itemView.timestamp.text = notification.Timestamp.dateTimeParsed()
            itemView.setOnClickListener { listener(notification) }
        }


        fun showVehicleAddedItemView(itemView: View, notification: Notification){
            itemView.description.text = "vehicle has been accepted"
            itemView.image.imageResource = notification.vehicle!!.getIconResource()

        }

        fun showTransactionPaidItemView(itemView: View, notification: Notification){

            if(notification.transaction?.origin?.openToll == true){
                itemView.description.text = "Passage on ${notification.transaction?.origin?.name} has been paid"
            }else {
                itemView.description.text = "transaction from ${notification.transaction?.origin?.name} to ${notification.transaction?.destination?.name}"
            }

            itemView.image.imageResource = R.drawable.ic_toll_green
        }

        fun showTransactionDetectedItemView(itemView: View, notification: Notification){
            if(notification.transaction?.origin?.openToll == true){
                itemView.description.text = "Passed on ${notification.transaction?.origin?.name}"
            }else {
                itemView.description.text = "transaction from ${notification.transaction?.origin?.name} to ${notification.transaction?.destination?.name}"
            }
            itemView.image.imageResource = notification.transaction?.vehicle!!.getIconResource()

        }


    }

}
