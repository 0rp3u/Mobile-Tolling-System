package pt.isel.ps.LI61N.g30.server.api.input;

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class InputTicket(
        val user_id: Long,
        val amount: Double,
        val reason: String?,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
        val timestamp: Date
)
