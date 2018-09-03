package pt.isel.ps.g30.tollingsystem.data.api.model

import java.util.*

data class TransactionInfo(
        val vehicleId: Int,
        val open: TollPassageInfo,
        val close: TollPassageInfo
)