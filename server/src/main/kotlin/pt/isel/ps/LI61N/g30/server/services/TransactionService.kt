package pt.isel.ps.LI61N.g30.server.services

import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.*
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TollRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TransactionRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TripRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository
import pt.isel.ps.LI61N.g30.server.logic.gis.CountPointsWithinPolygon
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromOwner
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromTrip
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.model.domain.Trip

@Service
class TransactionService(
        private val transactionRepository: TransactionRepository,
        private val vehicleRepository: VehicleRepository,
        private val tollRepository: TollRepository,
        private val tripRepository: TripRepository,
        private val tollService: TollService
) {

    fun beginTransaction(vehicle_id: Long, toll_id: Long, timestamp: Date, enforcementPoints: Array<GeoLocation>?, user: User): Transaction{
        //check if the same vehicle is not already in an uncompleted transaction
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("Vehicle not found.") }
        if(isVehicleRestrictedByTrip(vehicle)) throw Exception("Vehicle is not eligible for this action.")

        val trip = Trip(vehicle = vehicle)
        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Invalid Toll.") }

        //Toll Enforcement check
        val enforcement_sample_size = enforcementPoints?.size
        val enforcement_interceptions= enforcementPoints?.let { tollService.TollCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.ENTRY) }

        //todo Toll type enum and check for one-way tolls here???
        tripRepository.save(trip)
        return transactionRepository.save(createTransaction(trip, toll, timestamp, TransactionType.BEGIN, enforcement_sample_size, enforcement_interceptions))
    }

    fun endTransaction(vehicle_id: Long, toll_id: Long, timestamp: Date, trip_id: Long, enforcementPoints: Array<GeoLocation>?, user: User): Transaction{
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

        val enforcement_sample_size = enforcementPoints?.size
        val enforcement_interceptions= enforcementPoints?.let { tollService.TollCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.EXIT) }


        return transactionRepository.save(createTransaction(trip, toll, timestamp, TransactionType.END, enforcement_sample_size, enforcement_interceptions)).also {
            tripRepository.save(trip.apply { this.state =  TripState.AWAITING_CONFIRMATION})
        }
    }

    fun isVehicleRestrictedByTrip(vehicle: Vehicle): Boolean{
        val trip = tripRepository.findOneByVehicleOrderByCreatedDesc(vehicle)
        if(!trip.isPresent) return true
        return with(trip.get()){ state != TripState.INCOMPLETE}
    }

    //debug
    fun getAll(user: User): List<Transaction>{
        val res = transactionRepository.findByOrderByTimestampDesc()
        return res
    }

}