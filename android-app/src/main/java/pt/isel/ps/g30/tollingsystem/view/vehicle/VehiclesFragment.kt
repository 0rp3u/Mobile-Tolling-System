package pt.isel.ps.g30.tollingsystem.view.vehicle


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.progress_bar.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import kotlinx.android.synthetic.main.fragment_vehicles.*
import pt.isel.ps.g30.tollingsystem.extension.*
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclesFragPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseFragment
import javax.inject.Inject

class VehiclesFragment: BaseFragment<VehiclesFragPresenter, VehiclesFragmentView>(), VehiclesFragmentView{



    lateinit var vehicleRecyclerViewAdapter: VehicleRecyclerViewAdapter

    @Inject
    override lateinit var presenter: VehiclesFragPresenter

    override fun injectDependencies() {
       app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            val view = inflater.inflate(R.layout.fragment_vehicles, container, false)
            vehicleRecyclerViewAdapter = VehicleRecyclerViewAdapter {
                startActivity<VehicleActivity>(
                        VehicleActivity.EXTRA_VEHICLE_ID to it.id,
                        VehicleActivity.EXTRA_VEHICLE_LICENSE_PLATE to it.licensePlate
                )
            }

            return view
        } catch (e: Exception) {
            Log.e("frag vehicles", "onCreateView", e)
            throw e
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.apply{
            adapter = vehicleRecyclerViewAdapter
            layoutManager = LinearLayoutManager(activity)

        }

        presenter.getVehicleList()

        add_fab.setOnClickListener{ _ -> toast("open add vehicle Activity") }
    }

    override fun showVehicleList(list: List<Vehicle>) {
        vehicleRecyclerViewAdapter.vehicleList = list
    }


    override fun showLoadingIndicator() {
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }


    override fun showDoneMessage(message:String?) {
        snackbar(view!!, message ?: "done")
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if(action != null)
            longSnackbar(view!!, error ?: "error, something when wrong","repeat?", action)
        else
            snackbar(view!!, error ?: "error, something when wrong")
    }

    override fun onDestroyView() {
        presenter.cancelRequest()
        super.onDestroyView()
    }

}
