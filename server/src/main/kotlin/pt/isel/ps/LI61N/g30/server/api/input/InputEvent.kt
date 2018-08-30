package pt.isel.ps.LI61N.g30.server.api.input

import com.fasterxml.jackson.annotation.JsonFormat
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*

data class InputEvent(
        val vehicle_id: Long,
        val toll: Long,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
        val timestamp: Date,
        val geo_accuracy: Long?,
        val geoLocations: Array<GeoLocation>?
)