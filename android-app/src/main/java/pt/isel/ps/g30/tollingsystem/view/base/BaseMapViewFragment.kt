package pt.isel.ps.g30.tollingsystem.view.base


import android.annotation.SuppressLint
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapsInitializer
import android.util.Log
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.fragment_location.*
import pt.isel.ps.g30.tollingsystem.R
import pt.isel.ps.g30.tollingsystem.presenter.base.BasePresenter


abstract class BaseMapViewFragment<P: BasePresenter<V>, in V> : BaseView, BaseFragment<P,V>(), OnMapReadyCallback  {

    private val TAG = this.javaClass.simpleName

    protected lateinit var mMap : GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.d(TAG, "onCreate")
        return inflater.inflate(R.layout.fragment_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        mapView.onCreate(savedInstanceState)
        mapView.onResume() // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync(this@BaseMapViewFragment)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
        if(::mMap.isInitialized)
            mMap.isMyLocationEnabled = false
        mapView?.onDestroy()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.apply {
            uiSettings.isCompassEnabled = false
            uiSettings.isMapToolbarEnabled = false
            mapReady()
        }
    }

    abstract fun mapReady()


    override fun onStop() {
        super.onStop()
        Log.d(TAG, "on stop")
        mapView?.onStop()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "on start")
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "on resume")
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "on pause")
        mapView?.onPause()
    }

    @SuppressLint("MissingPermission")
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "on destroy")

        if(::mMap.isInitialized) mMap.isMyLocationEnabled = false 

        mapView?.onDestroy()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(TAG, "on low memory")
        mapView?.onLowMemory()
    }

}