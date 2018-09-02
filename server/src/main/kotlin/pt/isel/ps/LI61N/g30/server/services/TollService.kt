package pt.isel.ps.LI61N.g30.server.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.logic.gis.CountPointsWithinPolygon
import pt.isel.ps.LI61N.g30.server.logic.gis.GetNClosestTolls
import pt.isel.ps.LI61N.g30.server.model.domain.Toll
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TollRepository
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.math.BigInteger
import java.util.*
import javax.persistence.EntityManager

@Service
class TollService(
    val tollRepository: TollRepository,
    val entityManager: EntityManager
){

    fun getNearestTolls(location: GeoLocation, nTolls: Optional<Int>): List<Long> {
        return if(nTolls.isPresent){
            getNearestTolls(location, nTolls.get())
        }else{
            getNearestTolls(location)
        }
    }

    private fun getNearestTolls(location: GeoLocation, nTolls: Int = GetNClosestTolls.MAX_TOLLS): List<Long> =
        entityManager.createNativeQuery(GetNClosestTolls.query)
            .setParameter("n", nTolls)
            .setParameter("lon", location.longitude)
            .setParameter("lat", location.latitude)
            .resultList.map { with(it as BigInteger) { longValueExact() } }

    fun TollCheck(geoLocations: Array<GeoLocation>, toll: Toll, area: CountPointsWithinPolygon.Area) : Int =
            entityManager.createNativeQuery(CountPointsWithinPolygon.getQuery(geoLocations, area))
                .setParameter("_toll_id", toll.id)
                .singleResult.let { with(it as BigInteger) { intValueExact() } }

    fun getTolls(): List<Toll>{
        return tollRepository.findAll().toList()
    }
}