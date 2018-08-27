package pt.isel.ps.LI61N.g30.server.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_trip_amendment")
data class TripAmendment(

        @Id
        @GeneratedValue
        val id: Long = -1,

        @JoinColumn(name = "trip_id")
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val trip: Trip,

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