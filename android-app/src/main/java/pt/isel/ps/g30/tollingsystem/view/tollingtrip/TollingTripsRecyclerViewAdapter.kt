package pt.isel.ps.g30.tollingsystem.view.tollingtrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.extension.dateTimeParsed

class TollingTripsRecyclerViewAdapter(val listener: (TollingTrip) -> Unit) : RecyclerView.Adapter<TollingTripsRecyclerViewAdapter.ViewHolder>() {

    var historyList = listOf<TollingTrip>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history, listener)
    }

    override fun getItemCount() = historyList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(history: TollingTrip, listener: (TollingTrip) -> Unit) {

            itemView.date_origin.text = history.originTimestamp.dateTimeParsed()
            itemView.from.text = history.origin.name
            itemView.date_destination.text = history.destTimestamp.dateTimeParsed()
            itemView.destination.text = history.destination.name
            itemView.setOnClickListener { listener(history) }
        }

    }

}
