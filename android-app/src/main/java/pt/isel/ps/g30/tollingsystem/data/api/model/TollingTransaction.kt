package pt.isel.ps.g30.tollingsystem.data.api.model

import java.util.*

data class TollingTransaction(
        val id: Int,
        val vehicleId: Int,
        val openPlaza: Int,
        val openTimestamp: Date,
        val closePlaza: Int? = null,
        val closeTimestamp: Date,
        val paid: Boolean = false

)