package pt.isel.ps.LI61N.g30.server.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Polygon
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name="mts_toll")
data class Toll(

        @Id
        @GeneratedValue
        var id: Long = -1,

        @OneToMany(
                mappedBy = "toll",
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        @JsonIgnore
        val event: MutableList<Event>,

        @Column(name="name", unique = true)
        val name: String,

        @Enumerated(EnumType.STRING)
        val toll_type: TollType,

        @Column
        val geolocation: Point?,

        @Column
        val entry_area: Polygon?,

        @Column
        val exit_area: Polygon?,

        @Column(name="geolocation_latitude")
        val geolocation_latitude: Float,

        @Column(name="geolocation_longitude")
        val geolocation_longitude: Float,

        @Column(name="azimuth", unique = true, nullable = true)
        val azimuth: Float?,

        @Column(name="region", unique = true, nullable = true)
        val region: String?,

        @Column(name="road", unique = true, nullable = true)
        val road: String?,

        @Column
        @CreationTimestamp
        val created: Date? = Date()
){
        companion object {

            //private val geometryFactory = GeometryFactory()
            fun create(id: Long, name: String, toll_type: String, geolocation: Point, entry_area: Polygon?): Toll{
                    return Toll(
                            id = id,
                            event = mutableListOf(),
                            name = name,
                            toll_type = TollType.NORMAL,
                            geolocation = geolocation,
                            entry_area = entry_area,
                            exit_area = null,
                            geolocation_latitude = 2f,
                            geolocation_longitude = 1f,
                            azimuth = null,
                            region = null,
                            road = null
                    )
            }
        }
}