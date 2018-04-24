package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_vehicle.view.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.model.Vehicle

class VehicleRecyclerViewAdapter(val listener: (Vehicle) -> Unit) : RecyclerView.Adapter<VehicleRecyclerViewAdapter.ViewHolder>() {

    var vehicleList = listOf<Vehicle>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_vehicle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.bind(vehicle, listener)
    }

    override fun getItemCount() = vehicleList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(vehicle: Vehicle, listener: (Vehicle) -> Unit) {
            itemView.vehicle_plate_text_view.text = vehicle.licensePlate
            itemView.setOnClickListener { listener(vehicle) }
        }

    }

}
