package pt.isel.ps.LI61N.g30.server.model.domain

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Embeddable
data class TollTransactionId(
        @Enumerated(EnumType.STRING)
        val type: EventType,
        val toll_id: Long,
        val transaction_id: Long
) : Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true

        if (o == null || javaClass != o.javaClass)
            return false

        val that = o as TollTransactionId
        return Objects.equals(toll_id, that.toll_id) && Objects.equals(transaction_id, that.transaction_id) && Objects.equals(type, that.type)
    }

    override fun hashCode(): Int =
            Objects.hash(toll_id, transaction_id, type)
}