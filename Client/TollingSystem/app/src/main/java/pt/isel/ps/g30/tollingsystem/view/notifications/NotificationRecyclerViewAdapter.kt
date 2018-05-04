package pt.isel.ps.g30.tollingsystem.view.notifications

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_notification.view.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.model.Notification

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
            itemView.description_text_view.text = notification.description
            itemView.setOnClickListener { listener(notification) }
        }

    }

}
