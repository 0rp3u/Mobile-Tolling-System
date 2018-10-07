package pt.isel.ps.LI61N.g30.server.api.output

import com.fasterxml.jackson.annotation.JsonFormat
import pt.isel.ps.LI61N.g30.server.model.domain.*
import pt.isel.ps.LI61N.g30.server.services.EventService.Companion.getFirstEvent
import pt.isel.ps.LI61N.g30.server.services.EventService.Companion.getLastEvent
import java.util.*

data class OutputTransaction(
        var id: Long,
        var user_id: Long,
        var state: TransactionState,
        val vehicle_id: Long,
        var billing: Double?,
        val begin_toll: Long,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
        val begin_timestamp: Date,
        val end_toll: Long,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
        val end_timestamp: Date,
        val amendments: MutableList<TransactionAmendment>,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
        val created: Date?,
        @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
        var updated: Date?
)

fun createOutputTransaction(transaction: Transaction): OutputTransaction{
    return with(transaction){
        val firstEvent = getFirstEvent(event)
        val lastEvent = getLastEvent(event)

        OutputTransaction(
                id,
                vehicle.owner.id,
                state,
                vehicle.id,
                billing,
                begin_toll = firstEvent.toll.id,
                begin_timestamp = firstEvent.timestamp,
                end_toll = lastEvent.toll.id,
                end_timestamp = lastEvent.timestamp,
                amendments = amendments,
                created = created,
                updated = updated
        )
    }
}