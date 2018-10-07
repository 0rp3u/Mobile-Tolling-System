package pt.isel.ps.LI61N.g30.server.utils

import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class TollPassageInfo(
        val geoLocation: GeoLocation,
        val bearing: Double,
        val accuracy: Double,
        val timestamp: Long
)