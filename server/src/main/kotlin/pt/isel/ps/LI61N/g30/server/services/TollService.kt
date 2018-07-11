package pt.isel.ps.LI61N.g30.server.services

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Point
import org.hibernate.spatial.jts.EnvelopeAdapter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import pt.isel.ps.LI61N.g30.server.model.domain.Toll
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TollRepository
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingService
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.math.BigInteger
import java.util.*
import javax.persistence.EntityManager

@Service
class TollService(
    val tollRepository: TollRepository,
    val entityManager: EntityManager
){
    private val MAX_TOLLS: Int = 90
    val getNearestQuery =
                "select mts_toll.id\n" +
                "FROM mts_toll\n" +
                "order by st_distance(\n" +
                "ST_Transform(ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), 2100),\n" +
                "ST_Transform(geolocation, 2100)\n" +
                ") asc limit :n"

//    fun getNearestTolls(location: Point, n: Int): List<Toll>{
//        val p = geometryFactory.createPoint(Coordinate(10.0, 5.0))
//        return listOf()
//    }

    fun getNearestTolls(location: GeoLocation, nTolls: Optional<Int>): List<Long> {
        return if(nTolls.isPresent){
            getNearestTolls(location, nTolls.get())
        }else{
            getNearestTolls(location)
        }
    }

    private fun getNearestTolls(location: GeoLocation, nTolls: Int = MAX_TOLLS): List<Long> =
        entityManager.createNativeQuery(getNearestQuery)
            .setParameter("n", nTolls)
            .setParameter("lon", location.longitude)
            .setParameter("lat", location.latitude)
            .resultList.map { with(it as BigInteger) { longValueExact() } }


    fun getTolls(): Page<Toll>{
        return tollRepository.findAll(Pageable.unpaged())
    }
}