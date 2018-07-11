package pt.isel.ps.LI61N.g30.server.api.controllers.input;

import java.util.*

data class InputTicket(
        val user_id: Long,
        val amount: Long,
        val reason: String?,
        val timestamp: Date
)
