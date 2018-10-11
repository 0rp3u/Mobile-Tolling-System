package pt.isel.ps.g30.tollingsystem.data.api.model

import com.google.android.gms.maps.model.LatLng
import java.util.*

data class Point(
        val geoLocation : LatLng,
        val bearing: Float,
        val accuracy: Float,
        val timestamp: Long
)