package pt.isel.ps.g30.tollingsystem.data.api.model

data class TollingPlaza(

        val name: String,

        val concession: String,

        val geolocation_latitude: Double,

        val geolocation_longitude: Double,

        val open_toll: Boolean = false,

        var id: Int = 0
)