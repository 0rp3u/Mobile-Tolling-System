package pt.isel.ps.g30.tollingsystem.data.api.model

import java.util.*

data class TollingTransaction(
        val id: Int,
        val vehicle_id: Int,
        val begin_toll: Int,
        val begin_timestamp: String,
        val end_toll: Int,
        val end_timestamp: String,
        val billing: Float? = null
)