package pt.isel.ps.g30.tollingsystem.view.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import javax.inject.Inject
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.maps.android.ui.IconGenerator
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.dialog_vehicles.view.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.dialog_cancel_transaction.view.*
import kotlinx.android.synthetic.main.dialog_toll_plaza_options.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.imageView
import org.jetbrains.anko.textView
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.data.database.model.UnvalidatedTransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.injection.module.PresentersModule
import pt.isel.ps.g30.tollingsystem.presenter.navigation.NavigationFragPresenter
import pt.isel.ps.g30.tollingsystem.view.base.BaseMapViewFragment
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.extension.*
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivity
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleRecyclerViewAdapter

class NavigationViewFragment : BaseMapViewFragment<NavigationFragPresenter, NavigationFragmentView>(), NavigationFragmentView {

    private lateinit var  mFusedLocationClient: FusedLocationProviderClient

    companion object {
        private val TAG = NavigationViewFragment::class.simpleName
    }

    @Inject
    override lateinit var presenter: NavigationFragPresenter


    private var tollMarkers: List<Marker> = listOf()
    private var vehicleMarker: Marker? = null
    private var temporaryTransaction: UnvalidatedTransactionInfo? = null

    private var trackMode: Boolean = false


    override fun injectDependencies() {
        app.applicationComponent
                .plus(PresentersModule())
                .injectTo(this)
    }

    @SuppressLint("MissingPermission")
    override fun mapReady() {
        askPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this@NavigationViewFragment.requireContext())

            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = false

            mFusedLocationClient.lastLocation.addOnSuccessListener {
                it?.let {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f), 1000, null)
                }
            }

            presenter.setUpMap()

        }.onDeclined { e ->
            AlertDialog.Builder(activity)
                    .setMessage("We need location permission to provide our services!") //TODO use @String
                    .setPositiveButton("yes") { _, _ -> e.askAgain() }
                    .setNegativeButton("no") { dialog, _ -> dialog.dismiss() }
                    .show()

        }

    }

    private fun onMarkerInfoClick(tollingPlaza: TollingPlaza) {
        if (vehicleMarker == null){ toast("no active vehicle"); return }

        val dialogView = layoutInflater.inflate(R.layout.dialog_toll_plaza_options, null).apply {
            toll.text = tollingPlaza.name
            concecion.text = tollingPlaza.concession
        }

        AlertDialog.Builder(this.requireContext())
                .setTitle("${tollingPlaza.name} toll options")
                .setView(dialogView)
                .setPositiveButton("yes") { dialogInterface, i ->

                    if(temporaryTransaction?.origin != null && temporaryTransaction?.origin?.plaza?.openToll == false)
                        presenter.finishTransaction(tollingPlaza)
                    else
                        presenter.startTransaction(tollingPlaza)

                    dialogInterface.dismiss()
                }
                .setNegativeButton("no") { dialogInterface, i -> dialogInterface.cancel() }
                .create()
                .show()
    }


    override fun showPlazaLocations(list: List<TollingPlaza>) {
        mMap.apply {
            tollMarkers = list.map {
                //adds all markers for all tolling booths
                addMarker(MarkerOptions()
                        .snippet(it.concession)
                        .title(it.name)
                        .icon(this@NavigationViewFragment.requireContext().BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_blue))
                        .position(LatLng(it.lat, it.Lng))).apply { tag = it }

            }
            setOnInfoWindowClickListener { mark -> if (mark.tag is TollingPlaza) onMarkerInfoClick(mark.tag as TollingPlaza) }
        }
    }


    /** Vehicles **/

    override fun showActiveVehicle(vehicle: Vehicle?) {
        vehicle?.let {
            temporaryTransaction?.vehicle = it
            showVehicleLocation(it)
            addActiveVehicleToMapTop(it)

            fab.setImageResource(R.drawable.ic_block_black_24dp)
            fab.setOnClickListener { view ->
                presenter.removeActiveVehicle(vehicle)
                fab.setImageResource(R.drawable.ic_navigation_black_24dp)
            }
        } ?: fab.setOnClickListener { view -> presenter.prepareVehiclesDialog(); }
    }

    override fun removeActiveVehicle() {
        upper_vehicle_info.removeAllViewsInLayout()
        upper_vehicle_info.visibility = View.GONE

        vehicleMarker?.remove()
        vehicleMarker = null
        trackMode = false

        fab.setImageResource(R.drawable.ic_navigation_black_24dp)
        fab.setOnClickListener { view ->
            toast("fab click remove")
            presenter.prepareVehiclesDialog()
        }
    }


    @SuppressLint("MissingPermission")
    private fun showVehicleLocation(vehicle: Vehicle) {

        mFusedLocationClient.let {

            it.lastLocation.addOnSuccessListener(requireActivity()) { location ->
                // Got last known location. In some rare situations this can be null.
                location?.apply { LatLng(latitude, longitude).let {
                    mMap.apply {
                        moveCamera(CameraUpdateFactory.newLatLng(it))
                        animateCamera(CameraUpdateFactory.zoomTo(10.0f), 1000, null)
                        addMarker(MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromBitmap(createVehicleMarkerIcon(vehicle)))
                                .position(it)
                        ).also { vehicleMarker = it }

                        setOnMarkerClickListener {
                            if (it == vehicleMarker) {
                                this@NavigationViewFragment.startActivity<VehicleActivity>(
                                        VehicleActivity.EXTRA_VEHICLE_ID to vehicle.id,
                                        VehicleActivity.EXTRA_VEHICLE_LICENSE_PLATE to vehicle.licensePlate)
                            }
                            false
                        }
                    }
                }
                }
            }

            mMap.setOnMyLocationChangeListener { //deprecated, but it's fine since we only want the vehicle icon to follow the maps blue dot
                if(it != null) try{
                    vehicleMarker?.position = LatLng(it.latitude, it.longitude)
                    if(trackMode){
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(5f), 3000, null)
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                                CameraPosition.Builder()
                                        .target(vehicleMarker?.position)
                                        .zoom(17f)
                                        .tilt(90f)
                                        .bearing(it.bearing ?: 10f)
                                        .build()
                        ))
                    }
                }catch (e:Exception){
                    Log.e(TAG, e.message)
                }
            }
        }
    }

    private fun addActiveVehicleToMapTop(vehicle: Vehicle) {
        upper_vehicle_info.apply {
            removeAllViewsInLayout()
            visibility = View.VISIBLE
            textView().text = vehicle.licensePlate
            imageView().image = requireContext().getDrawable(vehicle.getIconResource())
            if(trackMode)  background.alpha = 130 else background.alpha = 30

            setOnClickListener {
                trackMode = !trackMode
                if(trackMode){  background.alpha = 130; toast("following vehicle")} else background.alpha = 30
            }
        }
    }

    override fun showVehiclesDialog(list: List<Vehicle>) {

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this.requireContext())
                .setTitle("Choose vehicle")

        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_vehicles, null)

        dialogView.recycler_view.layoutManager = LinearLayoutManager(dialogBuilder.context)
        dialogBuilder.setView(dialogView)
        val alertDialog: AlertDialog = dialogBuilder.create()


        dialogView.recycler_view.adapter = VehicleRecyclerViewAdapter { it ->
            presenter.setActiveVehicle(it)
            alertDialog.dismiss()

        }.apply { vehicleList = list }

        alertDialog.show()

    }


    /** Transactions **/

    override fun showActiveTransaction(transaction: UnvalidatedTransactionInfo) {
        if(transaction.origin?.plaza?.openToll == true) return
        Log.d(TAG, "show transaction view ${transaction.vehicle} : ${transaction.origin} -> ${transaction.destination}")
        val icon = this@NavigationViewFragment.requireContext().BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_green)
        transaction.let {
            tollMarkers.forEach {
                if(it.tag is TollingPlaza && (it.tag as TollingPlaza).id == transaction.origin?.plaza?.id){
                    it.setIcon(icon)
                }
            }
        }
        fab.setOnClickListener { showCancelActiveTransactionDialog(transaction) }
    }

    override fun removeActiveTransaction() {
        val icon = requireContext().BitmapDescriptorFactoryfromVector(R.drawable.ic_toll_blue)
        tollMarkers.forEach{
            if (it.tag is TollingPlaza){
                it.setIcon(icon)
                Log.d(TAG, "Cleaned ${(it.tag as TollingPlaza).name} icon")
            }
        }

        fab.setOnClickListener { view ->
            temporaryTransaction?.vehicle?.let { presenter.removeActiveVehicle(it)} ?: removeActiveVehicle()
            fab.setImageResource(R.drawable.ic_navigation_black_24dp)
        }
    }



    override fun showCancelActiveTransactionDialog(transaction: UnvalidatedTransactionInfo) {

        val dialogView = layoutInflater.inflate(R.layout.dialog_cancel_transaction, null).apply {
            timestamp.text = transaction.origin?.timestamp?.dateTimeParsed()
            plate.text = transaction.vehicle?.licensePlate
            tare.imageResource = transaction.vehicle!!.getIconResource() //<- !! fine because the transaction is active in here
            toll_name.text = transaction.origin?.plaza?.name
            toll_concecion.text = transaction.origin?.plaza?.concession
        }

        AlertDialog.Builder(this.requireContext())
                .setTitle("${transaction.origin?.plaza?.name} toll options")
                .setView(dialogView)
                .setPositiveButton("yes, cancel!") { dialogInterface, i -> presenter.cancelActiveTransaction(transaction); dialogInterface.dismiss() }
                .setNegativeButton("no") { dialogInterface, i -> dialogInterface.cancel() }
                .create()
                .show()
    }

    override fun setCurrentTransaction(transaction: UnvalidatedTransactionInfo) {
        this.temporaryTransaction = transaction
    }

    override fun getCurrentTransaction() = temporaryTransaction



    override fun showDoneMessage(message: String?) {
        snackbar(view!!, message ?: "done")
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if (action != null)
            longSnackbar(view!!, error ?: "error, something when wrong", "repeat?", action)
        else
            snackbar(view!!, error ?: "error, something when wrong")
    }

    override fun showLoadingIndicator() {
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }

    private fun createVehicleMarkerIcon(vehicle: Vehicle): Bitmap {
        val text = TextView(requireContext())
        text.text = vehicle.licensePlate

        val lay = LinearLayout(requireContext())
        lay.orientation = LinearLayout.VERTICAL
        lay.addView(text)
        val img = ImageView(requireContext())
        img.image = requireContext().getDrawable(vehicle.getIconResource())
        lay.addView(img)

        val generator = IconGenerator(requireContext())
        generator.setColor(Color.TRANSPARENT)
        generator.setContentView(lay)
        return generator.makeIcon()
    }
}