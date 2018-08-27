package pt.isel.ps.LI61N.g30.server.model.domain

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Embeddable
data class TollTripId(
        @Enumerated(EnumType.STRING)
        val type: TransactionType,
        val toll_id: Long,
        val trip_id: Long
) : Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true

        if (o == null || javaClass != o.javaClass)
            return false

        val that = o as TollTripId
        return Objects.equals(toll_id, that.toll_id) && Objects.equals(trip_id, that.trip_id) && Objects.equals(type, that.type)
    }

    override fun hashCode(): Int =
            Objects.hash(toll_id, trip_id, type)
}