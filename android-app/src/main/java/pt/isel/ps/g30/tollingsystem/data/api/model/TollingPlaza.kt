package pt.isel.ps.g30.tollingsystem.data.api.model

data class TollingPlaza(

        val name: String,

        val concession: String,

        val latLong: LatLong,

        val openToll: Boolean = false,

        var id: Int = 0
)