package pt.isel.ps.LI61N.g30.server.model.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_vehicle")
data class Vehicle(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = -1,

        @Column(name= "plate")
        val plate: String,

        @Enumerated(EnumType.ORDINAL)
        var tier: VehicleType,

        @JsonIgnoreProperties("vehicles")
        @JoinColumn(name = "user_id", nullable = false)
        @ManyToOne
        val owner: User,

        @Column
        @CreationTimestamp
        var created: Date? = Date()
)