package pt.isel.ps.LI61N.g30.server.model.domain


import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_transaction")
data class Transaction(

        @AttributeOverride(name="t_id", column = Column(name = "id"))
        @EmbeddedId
        val tolltripId: TollTripId,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val uid: Transaction_uid = Transaction_uid(-1),

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @MapsId("trip_id")
        val trip: Trip,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @MapsId("toll_id")
        val toll: Toll,

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

fun createTransaction(trip: Trip, toll: Toll, timestamp: Date, type: TransactionType, enforcement_sample: Int?, enforcement_interceptions: Int?): Transaction{
        return Transaction(
                tolltripId = pt.isel.ps.LI61N.g30.server.model.domain.TollTripId(trip_id = trip.id, toll_id = toll.id, type = type),
                toll = toll,
                trip = trip,
                timestamp = timestamp,
                enforcement_sample = enforcement_sample,
                enforcement_interceptions = enforcement_interceptions
        )
}

@Entity
data class Transaction_uid (
        @Id
        @GeneratedValue(strategy =  GenerationType.IDENTITY)
        val uid: Long = -1
)

enum class TransactionType{
        BEGIN,
        END
}