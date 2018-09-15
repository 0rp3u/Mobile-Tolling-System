package pt.isel.ps.LI61N.g30.server.model.domain


import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_event")
data class Event(

        @AttributeOverride(name="t_id", column = Column(name = "id"))
        @EmbeddedId
        val tolltransactionId: TollTransactionId,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val uid: Event_uid = Event_uid(-1),

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @MapsId("transaction_id")
        val transaction: Transaction,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @MapsId("toll_id")
        var toll: Toll,

        @Column(name = "event_date")
        val timestamp: Date,

        @Column(name = "direction")
        val direction: String? = null,

        @Column(name = "issuer")
        val issuer: String? = null,

        @Column(name = "enforcement_sample_size")
        val enforcement_sample: Int?,

        @Column(name = "enforcement_interceptions")
        val enforcement_interceptions: Int?,

        @Column
        @CreationTimestamp
        val created: Date = Date()
)

fun createEvent(transaction: Transaction, toll: Toll, timestamp: Date, type: EventType, enforcement_sample: Int?, enforcement_interceptions: Int?): Event{
        return Event(
                tolltransactionId = pt.isel.ps.LI61N.g30.server.model.domain.TollTransactionId(transaction_id = transaction.id, toll_id = toll.id, type = type),
                toll = toll,
                transaction = transaction,
                timestamp = timestamp,
                enforcement_sample = enforcement_sample,
                enforcement_interceptions = enforcement_interceptions
        )
}

@Entity
data class Event_uid (
        @Id
        @GeneratedValue(strategy =  GenerationType.IDENTITY)
        val uid: Long = -1
)

enum class EventType{
        BEGIN,
        END
}