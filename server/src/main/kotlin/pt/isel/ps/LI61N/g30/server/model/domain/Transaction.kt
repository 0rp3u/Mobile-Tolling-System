package pt.isel.ps.LI61N.g30.server.model.domain

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_transaction")
data class Transaction(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = -1,

        @Enumerated(EnumType.STRING)
        var state: TransactionState = TransactionState.INCOMPLETE,

        @ManyToOne
        @JoinColumn(name = "vehicle", foreignKey = ForeignKey(name = "VEHICLE_FK"))
        val vehicle: Vehicle,

        @OneToMany(
                //mappedBy = "transaction",
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        val event: MutableList<Event> = mutableListOf(),

        @OneToMany(
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        val amendments: MutableList<TransactionAmendment> = mutableListOf(),

        @Column
        @CreationTimestamp
        val created: Date? = Date()

)

enum class TransactionState{
    INCOMPLETE,
    CANCELED,
    AWAITING_CONFIRMATION,
    CONFIRMED
}