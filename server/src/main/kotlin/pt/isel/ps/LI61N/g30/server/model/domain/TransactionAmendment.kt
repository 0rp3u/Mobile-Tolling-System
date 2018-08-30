package pt.isel.ps.LI61N.g30.server.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_transaction_amendment")
data class TransactionAmendment(

        @Id
        @GeneratedValue
        val id: Long = -1,

        @JoinColumn(name = "transaction_id")
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val transaction: Transaction,

        @Column
        val old_begin_toll: Long,

        @Column
        val old_end_toll: Long,

        @Column
        val new_begin_toll: Long,

        @Column
        val new_end_toll: Long,

        @Column
        @CreationTimestamp
        val created: Date? = Date()

)