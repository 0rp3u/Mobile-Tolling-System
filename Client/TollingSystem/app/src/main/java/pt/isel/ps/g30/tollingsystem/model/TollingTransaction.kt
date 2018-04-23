package pt.isel.ps.g30.tollingsystem.model

data class TollingTransaction(
        val openPlaza: Int,
        val closePlaza: Int?,
        val vehicle: Int

)