package pt.isel.ps.LI61N.g30.server.model.domain

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_trip")
data class Trip(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = -1,

        //string for now, implemenent with enum later
        @Column(name="state")
        var state: String = "incomplete",

        @ManyToOne
        @JoinColumn(name = "vehicle", foreignKey = ForeignKey(name = "VEHICLE_FK"))
        val vehicle: Vehicle,

        @OneToMany(
                //mappedBy = "trip",
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        val transaction: MutableList<Transaction> = mutableListOf(),

        @OneToMany(

                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        val amendments: MutableList<TripAmendment> = mutableListOf(),

        @Column
        @CreationTimestamp
        val created: Date? = Date()

)