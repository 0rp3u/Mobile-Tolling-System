package pt.isel.ps.g30.tollingsystem.data.api.model

data class TollingPlaza(

        val name: String,

        val concession: String,

        val location_latitude: Double,

        val geolocation_longitude: Double,

        val openToll: Boolean = false,

        var id: Int = 0
)