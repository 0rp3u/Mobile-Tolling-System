package pt.isel.ps.g30.tollingsystem.data.api.model

data class TollingPlaza(
        val id: Int,
        val name: String,
        val latLng: String //TODO create latlong model
)