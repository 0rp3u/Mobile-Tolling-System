package pt.isel.ps.g30.tollingsystem.view.tollingtrip

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_tolling_trip_details.*
import kotlinx.android.synthetic.main.template_trip_details.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.extension.*
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.tollingtrip.TollingTripDetailsPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import javax.inject.Inject



class TollingTripDetails : BaseActivity<TollingTripDetailsPresenter, TollingTripDetailsView>(), TollingTripDetailsView, OnMapReadyCallback {

    companion object {
        const val EXTRA_TRIP_ID = "EXTRA_TRIP_ID"
    }


    @Inject
    override lateinit var presenter: TollingTripDetailsPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tolling_trip_details)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false
        presenter.getTollingTrip(intent.getIntExtra(EXTRA_TRIP_ID,-1))
    }



    override fun showTrip(tollingTrip: TollingTrip) {

        val originLatLong = tollingTrip.origin.let { LatLng(it.lat, it.Lng) }
        val destinationLatLong = tollingTrip.destination.let { LatLng(it.lat, it.Lng) }
        map.trip_details.background.alpha = 130

        tollingTrip.origin.let{
            map.from.text = it.name
            map.date_origin.text = tollingTrip.originTimestamp.dateTimeParsed()

            mMap.addMarker(
                    MarkerOptions()
                            .title(it.name)
                            .snippet(it.concession)
                            .icon(BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_green))
                            .position(originLatLong)

            )
        }
        tollingTrip.destination.let{
            map.destination.text = it.name
            map.date_destination.text = tollingTrip.destTimestamp.dateTimeParsed()

            mMap.addMarker(
                    MarkerOptions()
                            .title(it.name)
                            .snippet(it.concession)
                            .icon(BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_red))
                            .position(destinationLatLong)

            )
        }

        mMap.animateCamera(CameraUpdateFactory.zoomTo(5f), 3000, null)
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                        .target(originLatLong)
                        .zoom(10f)
                        .tilt(90f)
                        .bearing(10f)
                        .build()

        ))
    }




    override fun showLoadingIndicator() {
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }


    override fun showDoneMessage(message:String?) {
        snackbar(this.map.view!!, message ?: "done")
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if(action != null)
            longSnackbar(this.map.view!!, error ?: "error, something when wrong","repeat?", action)
        else
            snackbar(this.map.view!!, error ?: "error, something when wrong")
    }

    override fun onDestroy() {
        presenter.cancelRequest()
        super.onDestroy()
    }

}