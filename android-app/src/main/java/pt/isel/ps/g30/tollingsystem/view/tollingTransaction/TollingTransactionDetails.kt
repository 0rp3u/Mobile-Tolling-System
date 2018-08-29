package pt.isel.ps.g30.tollingsystem.view.tollingTransaction

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_tolling_transaction_details.*
import kotlinx.android.synthetic.main.template_transaction_details.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.extension.*
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction.TollingTransactionDetailsPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseActivity
import javax.inject.Inject



class TollingTransactionDetails : BaseActivity<TollingTransactionDetailsPresenter, TollingTransactionDetailsView>(), TollingTransactionDetailsView, OnMapReadyCallback {

    companion object {
        const val EXTRA_Transaction_ID = "EXTRA_Transaction_ID"
    }


    @Inject
    override lateinit var presenter: TollingTransactionDetailsPresenter

    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tolling_transaction_details)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false
        presenter.getTollingTransaction(intent.getIntExtra(EXTRA_Transaction_ID,-1))
    }



    override fun showTransaction(tollingTransaction: TollingTransaction) {

        val originLatLong = tollingTransaction.origin.let { LatLng(it.lat, it.Lng) }
        val destinationLatLong = tollingTransaction.destination.let { LatLng(it.lat, it.Lng) }
        map.Transaction_details.background.alpha = 130


        if(tollingTransaction.origin == tollingTransaction.destination){
            map.open_toll_layout.visibility = View.VISIBLE
            map.closed_toll_layout.visibility = View.GONE


            tollingTransaction.origin.let{
                map.toll_name.text = it.name
                map.date.text = tollingTransaction.originTimestamp.dateTimeParsed()
                map.toll_layout.setOnClickListener { goToToll(originLatLong) }

                mMap.addMarker(
                        MarkerOptions()
                                .title(it.name)
                                .snippet(it.concession)
                                .icon(BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_green))
                                .position(originLatLong)

                )
            }


        }else{
            tollingTransaction.origin.let{
                map.from.text = it.name
                map.date_origin.text = tollingTransaction.originTimestamp.dateTimeParsed()
                map.origin_layout.setOnClickListener { goToToll(originLatLong) }

                mMap.addMarker(
                        MarkerOptions()
                                .title(it.name)
                                .snippet(it.concession)
                                .icon(BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_green))
                                .position(originLatLong)
                )
            }
            tollingTransaction.destination.let{
                map.destination.text = it.name
                map.date_destination.text = tollingTransaction.destTimestamp.dateTimeParsed()
                map.destination_layout.setOnClickListener { goToToll(destinationLatLong) }

                mMap.addMarker(
                        MarkerOptions()
                                .title(it.name)
                                .snippet(it.concession)
                                .icon(BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_red))
                                .position(destinationLatLong)
                )
            }
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

    fun goToToll(tollPosition: LatLng){
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f), 3000, null)
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                        .target(tollPosition)
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