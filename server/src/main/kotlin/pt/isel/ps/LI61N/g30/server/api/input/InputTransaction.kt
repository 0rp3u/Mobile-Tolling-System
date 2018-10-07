package pt.isel.ps.LI61N.g30.server.api.input

import com.fasterxml.jackson.annotation.JsonFormat
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import pt.isel.ps.LI61N.g30.server.utils.TollPassageInfo
import java.util.*

data class InputTransaction(
        val vehicle_id: Long,
        val begin_toll: Long,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss.SSS")
        val begin_timestamp: Date,
        val begin_geoLocations: Array<TollPassageInfo>?,
        val end_toll: Long,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss.SSS")
        val end_timestamp: Date,
        val end_geoLocations: Array<TollPassageInfo>?
)