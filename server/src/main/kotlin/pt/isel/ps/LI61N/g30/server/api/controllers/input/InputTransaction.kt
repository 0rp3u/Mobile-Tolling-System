package pt.isel.ps.LI61N.g30.server.api.controllers.input

import pt.isel.ps.LI61N.g30.server.utils.GeoLocation

data class InputTransaction(
    val vehicle_id: Long,
    val toll: Long,
    val timestamp: String,
    val geo_accuracy: Long?,
    val geoLocations: Array<GeoLocation>?
)