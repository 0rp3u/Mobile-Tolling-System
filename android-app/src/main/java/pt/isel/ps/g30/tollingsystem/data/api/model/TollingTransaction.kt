package pt.isel.ps.g30.tollingsystem.data.api.model

import java.util.*

data class TollingTransaction(
        val id: Int,
        val timestamp: Date,
        val vehicleId: Int,
        val openPlaza: Int,
        val closePlaza: Int? = null,
        val paid: Boolean = false
)