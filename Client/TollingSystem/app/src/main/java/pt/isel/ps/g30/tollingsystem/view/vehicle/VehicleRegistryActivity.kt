package pt.isel.ps.g30.tollingsystem.view.vehicle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pt.isel.ps.g30.tollingsystem.R

import kotlinx.android.synthetic.main.activity_vehicle_registry.*

class VehicleRegistryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_registry)
        setSupportActionBar(toolbar)
    }

}
