package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_vehicle_details.*
import kotlinx.android.synthetic.main.progress_bar.*
import org.jetbrains.anko.imageResource
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.extension.app
import pt.isel.ps.g30.tollingsystem.extension.getIconResource
import pt.isel.ps.g30.tollingsystem.extension.longSnackbar
import pt.isel.ps.g30.tollingsystem.extension.snackbar
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehicleDetailsPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseFragment
import javax.inject.Inject

class VehicleDetailsDetailFragment: BaseFragment<VehicleDetailsPresenter, VehicleDetailsView>(), VehicleDetailsView{

    private val TAG = VehicleDetailsDetailFragment::class.java.simpleName

    @Inject
    override lateinit var presenter: VehicleDetailsPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            return inflater.inflate(R.layout.fragment_vehicle_details, container, false)

        } catch (e: Exception) {
            Log.e(TAG, "onCreateView", e)
            throw e
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getVehicleDetails(arguments?.getInt(VehicleActivity.EXTRA_VEHICLE_ID)?:1)
    }

    override fun showVehicleBasicInfo(vehicle: Vehicle) {
        tare.imageResource = vehicle.getIconResource()
        license_plate.text = vehicle.licensePlate

    }

    override fun showVehicleTransactionNumber(TransactionNumber: Int) {
        Transaction_number.text = "$TransactionNumber"
    }

    override fun showVehiclePaidAmount(amount: Double) {
        poid_amount.text = "${String.format("%.2f",amount)} $"
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