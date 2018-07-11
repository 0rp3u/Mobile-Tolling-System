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

        @Column
        @CreationTimestamp
        val created: Date = Date()
)

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