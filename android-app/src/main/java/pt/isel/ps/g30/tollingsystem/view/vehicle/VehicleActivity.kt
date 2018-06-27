package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vehicle.*
import kotlinx.android.synthetic.main.template_tab_layout.*

import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.extension.*
import pt.isel.ps.g30.tollingsystem.view.common.CustomFragmentPagerAdapter
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragment

class
VehicleActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_VEHICLE_ID = "EXTRA_VEHICLE_ID"
        const val EXTRA_VEHICLE_LICENSE_PLATE = "EXTRA_VEHICLE_LICENSE_PLATE"
    }

    lateinit var customFragmentPagerAdapter: CustomFragmentPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)
        initToolbar()
        initViewPager()
        initTabLayout()
        val extras = intent.extras


        if (extras != null) {
            val id = extras.getInt(EXTRA_VEHICLE_ID)
            if(customFragmentPagerAdapter.fragments.isEmpty())
                customFragmentPagerAdapter.fragments = listOf(
                        VehicleDetailsDetailFragment().withArguments(
                                CustomFragmentPagerAdapter.ARG_NAME to "vehicle info",
                                "title" to "vehicle info",
                                EXTRA_VEHICLE_ID to id
                        ),
                        TollingTripsFragment().withArguments(
                                CustomFragmentPagerAdapter.ARG_NAME to "trips",
                                EXTRA_VEHICLE_ID to id

                        ))
        }
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
}
