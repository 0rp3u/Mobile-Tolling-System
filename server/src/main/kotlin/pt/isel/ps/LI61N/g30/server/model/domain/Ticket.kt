package pt.isel.ps.LI61N.g30.server.model.domain

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
data class Ticket(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="id")
        var id: Long = -1,

        @JoinColumn(name = "user_id")
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val user: User,

        @Column
        val amount: Double,

        @Column
        val reason: String?,

        @Column
        @CreationTimestamp
        val timestamp: Date? = Date()

)