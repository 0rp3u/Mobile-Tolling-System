package pt.isel.ps.g30.tollingsystem.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapsInitializer
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.transition.Visibility
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.location_fragment.*
import pt.isel.ps.g30.tollingsystem.R


class MapViewFragment : Fragment(), OnMapReadyCallback  {

    private lateinit var mMap : GoogleMap
    private lateinit var  mFusedLocationClient: FusedLocationProviderClient





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.location_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mMapView = mapView
        mMapView.onCreate(savedInstanceState)
        //mMapView.onResume() // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView.getMapAsync(this)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val mark = MarkerOptions()
                .snippet("23-AO-72")
                .title("veiculo ligeiro")
                .icon(bitmapDescriptorFromVector(this.requireContext(), R.drawable.ic_directions_car_black_24dp))

        askPermission(Manifest.permission.ACCESS_FINE_LOCATION){

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
            mFusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    val here = LatLng(location.latitude, location.longitude)
                    mark.position(here)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(here))
                    mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) )
                    mMap.addMarker(mark).showInfoWindow()
                }
            }

        }.onDeclined { e ->
            AlertDialog.Builder(activity )
                    .setMessage("We need location permission to provide our services!") //TODO use @String
                    .setPositiveButton("yes", { dialog, which -> e.askAgain() })
                    .setNegativeButton("no", { dialog, which -> dialog.dismiss()})
                    .show()

        }

    }

    private fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor {
        ContextCompat.getDrawable(context, vectorDrawableResourceId)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            //val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
            //vectorDrawable.setBounds(40, 20, vectorDrawable.intrinsicWidth + 40, vectorDrawable.intrinsicHeight + 20)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            draw(canvas)
            //ectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
        throw Exception()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}