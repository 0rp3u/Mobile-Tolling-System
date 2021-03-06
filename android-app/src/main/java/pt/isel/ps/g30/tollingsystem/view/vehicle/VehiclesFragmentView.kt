package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.view.View
import androidx.lifecycle.LiveData
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.view.base.BaseView

interface VehiclesFragmentView : BaseView {

    fun showVehicleList(list: LiveData<List<Vehicle>>)
    fun showDoneMessage(message:String? = null)
    fun showErrorMessage(error:String? = null, action: ((View) -> Unit)? = null)
    fun showLoadingIndicator()
    fun hideLoadingIndicator()

}