package pt.isel.ps.g30.tollingsystem.data.api.model

import com.google.android.gms.maps.model.LatLng
import java.util.*

data class Point(
        val position : LatLng,
        val bearing: Float,
        val timeStamp: Long
)