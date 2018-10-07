package pt.isel.ps.LI61N.g30.server.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_transaction")
data class Transaction(

        @Id
        @GeneratedValue
        var id: Long = -1,

        @Enumerated(EnumType.STRING)
        var state: TransactionState = TransactionState.AWAITING_CONFIRMATION,

        @ManyToOne
        @JoinColumn(name = "vehicle", foreignKey = ForeignKey(name = "VEHICLE_FK"))
        val vehicle: Vehicle,

        @Column
        var billing: Double? = null,

        @JsonIgnore
        @OneToMany(
                //mappedBy = "transaction",
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        var event: MutableList<Event> = mutableListOf(),

        @OneToMany(
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        val amendments: MutableList<TransactionAmendment> = mutableListOf(),

        @Column
        @CreationTimestamp
        val created: Date? = Date(),

        @Column
        @UpdateTimestamp
        var updated: Date? = Date()
)

enum class TransactionState{
    INCOMPLETE,
    AWAITING_CONFIRMATION,
    CONFIRMED,
    CLOSED,
    CANCELED
}