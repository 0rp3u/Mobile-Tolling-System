package pt.isel.ps.LI61N.g30.server.services

import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.*
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TollRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TransactionRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TripRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository
import pt.isel.ps.LI61N.g30.server.logic.CountPointsWithinPolygon
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromOwner
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromTrip
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*
import javax.persistence.EntityManager
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.model.domain.Trip

@Service
class TransactionService(
        private val transactionRepository: TransactionRepository,
        private val vehicleRepository: VehicleRepository,
        private val tollRepository: TollRepository,
        private val tripRepository: TripRepository,
        val entityManager: EntityManager
) {

    data class RegisterTransactionInputModel(
            val vehicle: Long,
            val toll_begin: Long,
            val timestamp_begin: String,
            val toll_end: Long,
            val timestamp_end: String
    )

    fun TollEnforcementCheck(geoLocations: Array<GeoLocation>, toll: Toll, area: String) : Int{
        val query = entityManager.createNativeQuery(CountPointsWithinPolygon.query)
                .setParameter("toll_id", toll.id)
                .setParameter("points", CountPointsWithinPolygon.generateQueryPoints(geoLocations))
                .singleResult as Int
        return query
    }

    fun beginTransaction(vehicle_id: Long, toll_id: Long, timestamp: String, enforcementPoints: Array<GeoLocation>?, user: User): Transaction{
        //check if the same vehicle is not already in an uncompleted transaction
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("Vehicle not found.") }
        if(isVehicleRestrictedByTrip(vehicle)) throw Exception("Vehicle is not eligible for this action.")

        val trip = Trip(vehicle = vehicle)
        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Invalid Toll.") }

        //Toll Enforcement check
        val enforcement_ratio=
                if(enforcementPoints != null)
                    TollEnforcementCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.ENTRY.name) / enforcementPoints.size as Float
                else null

        tripRepository.save(trip)
        return transactionRepository.save(createTransaction(trip, toll, Date(timestamp), TransactionType.BEGIN, enforcement_ratio))
    }

    fun endTransaction(vehicle_id: Long, toll_id: Long, timestamp: String, trip_id: Long, enforcementPoints: Array<GeoLocation>?, user: User): Transaction{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        val trip = tripRepository.findById(trip_id).orElseThrow { Exception("Trip does not exist.") }

        /*Validation*/
        if( !isVehicleFromOwner(vehicle, user))
            throw Exception("Vehicle does not belong to that Owner.")

        if(!isVehicleFromTrip(trip, vehicle))
            throw Exception("Vehicle does not belong to that trip.")

        if(trip.state == TripState.CANCELED)
            throw Exception("The trip is already cancelled.")

        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Toll does not exist.") }

        //Toll Enforcement check
        val enforcement_ratio=
                if(enforcementPoints != null)
                    TollEnforcementCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.EXIT.name) / enforcementPoints.size as Float
                else null

        return transactionRepository.save(createTransaction(trip, toll, Date(timestamp), TransactionType.END, enforcement_ratio)).also {
            tripRepository.save(trip.apply { this.state =  TripState.AWAITING_CONFIRMATION})
        }
    }

    fun cancelTrip(){

    }

    fun insertCompletedTransaction(){
        
    }

    fun isVehicleRestrictedByTrip(vehicle: Vehicle): Boolean{
        val trip = tripRepository.findOneByVehicleOrderByCreatedDesc(vehicle)
        if(!trip.isPresent) return true
        return with(trip.get()){ state != TripState.INCOMPLETE}
    }

    fun getLatestTrip(vehicle_id: Long, user: User): Trip{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        return tripRepository.findOneByVehicleOrderByCreatedDesc(vehicle).orElseThrow { Exception("No trip found for vehicle.") }
    }

    fun getAll(user: User): List<Transaction>{
        val res = transactionRepository.findByOrderByTimestampDesc()
        return res
    }

}