package pt.isel.ps.g30.tollingsystem.data.api.model


data class TransactionInfo(
        val vehicle_id: Int,
        val begin_toll: Int,
        val begin_timestamp: String,
        val begin_geoLocations: List<Point>,
        val end_toll: Int,
        val end_timestamp: String,
        val end_geoLocations: List<Point>

)