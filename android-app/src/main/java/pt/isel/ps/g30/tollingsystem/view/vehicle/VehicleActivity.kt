package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_trip_info.*
import kotlinx.android.synthetic.main.activity_vehicle.*
import kotlinx.android.synthetic.main.progress_bar.*
import kotlinx.android.synthetic.main.template_tab_layout.*

import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.extensions.*
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclePresenter
import pt.isel.ps.g30.tollingsystem.view.TextFragment
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import pt.isel.ps.g30.tollingsystem.view.common.CustomFragmentPagerAdapter
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragmentFragment
import javax.inject.Inject

class
VehicleActivity: BaseActivity<VehiclePresenter, VehicleView>(), VehicleView{

    companion object {
        const val EXTRA_VEHICLE_ID = "EXTRA_VEHICLE_ID"
        const val EXTRA_VEHICLE_LICENSE_PLATE = "EXTRA_VEHICLE_LICENSE_PLATE"
    }


    @Inject
    override lateinit var presenter: VehiclePresenter

    lateinit var customFragmentPagerAdapter: CustomFragmentPagerAdapter


    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)
        initToolbar()
        initViewPager()
        initTabLayout()

        presenter.getVehicle(1) //TODO id will come on an Intent

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        presenter.cancelRequest()

        super.onDestroy()
    }


    private fun initToolbar() {
        initToolbar(hasParent = true)
    }

    private fun initViewPager() {
        customFragmentPagerAdapter = CustomFragmentPagerAdapter(supportFragmentManager)
        view_pager.adapter = customFragmentPagerAdapter
    }

    private fun initTabLayout() {
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun showDoneMessage(message:String?) {
        snackbar(this.vehicles_activity_layout, message ?: "done")
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if(action != null)
            longSnackbar(this.vehicles_activity_layout, error ?: "error, something when wrong","repeat?", action)
        else
            snackbar(this.vehicles_activity_layout, error ?: "error, something when wrong")
    }


    override fun showVehicle(item: Vehicle) {

        if(customFragmentPagerAdapter.fragments.isEmpty())
            customFragmentPagerAdapter.fragments = listOf(
                    TextFragment().withArguments(
                            CustomFragmentPagerAdapter.ARG_NAME to "vehicle info",
                            "title" to "vehicle info"
                    ),
                    TollingTripsFragmentFragment().withArguments(
                            CustomFragmentPagerAdapter.ARG_NAME to "trips",
                            EXTRA_VEHICLE_ID to 1

                    ))

    }

    override fun showLoadingIndicator() {
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }

}
