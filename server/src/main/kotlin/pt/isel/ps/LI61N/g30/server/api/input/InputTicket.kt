package pt.isel.ps.LI61N.g30.server.api.input;

import java.util.*

data class InputTicket(
        val user_id: Long,
        val amount: Double,
        val reason: String?,
        val timestamp: Date
)
