package pt.isel.ps.LI61N.g30.server.services

import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.*
import pt.isel.ps.LI61N.g30.server.logic.gis.CountPointsWithinPolygon
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromOwner
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromTransaction
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*
import pt.isel.ps.LI61N.g30.server.model.domain.Event
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.*

@Service
class EventService(
        private val eventRepository: EventRepository,
        private val vehicleRepository: VehicleRepository,
        private val tollRepository: TollRepository,
        private val transactionRepository: TransactionRepository,
        private val tollService: TollService
) {

    fun beginEvent(vehicle_id: Long, toll_id: Long, timestamp: Date, enforcementPoints: Array<GeoLocation>?, user: User): Transaction{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("Vehicle not found.") }
        //check if the same vehicle is not already in an uncompleted event
        if(isVehicleRestrictedByTransaction(vehicle)) throw Exception("Vehicle is not eligible for this action.")
        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Invalid Toll.") }

        val transaction = Transaction(vehicle = vehicle)

        //Toll Enforcement check
        val enforcement_sample_size = enforcementPoints?.size
        val enforcement_interceptions= enforcementPoints?.let { tollService.TollCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.ENTRY) }

        //todo Toll type enum and check for one-way tolls here???
        val tran = transactionRepository.save(transaction)
        eventRepository.save(createEvent(transaction, toll, timestamp, EventType.BEGIN, enforcement_sample_size, enforcement_interceptions))
        return tran
    }

    fun endEvent(vehicle_id: Long, toll_id: Long, timestamp: Date, transaction_id: Long, enforcementPoints: Array<GeoLocation>?, user: User): Transaction{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        val transaction = transactionRepository.findById(transaction_id).orElseThrow { Exception("Transaction does not exist.") }

        /*Validation*/
        if( !isVehicleFromOwner(vehicle, user))
            throw Exception("Vehicle does not belong to that Owner.")

        if(!isVehicleFromTransaction(transaction, vehicle))
            throw Exception("Vehicle does not belong to that transaction.")

        if(transaction.state == TransactionState.CANCELED)
            throw Exception("The transaction is already cancelled.")

        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Toll does not exist.") }

        val enforcement_sample_size = enforcementPoints?.size
        val enforcement_interceptions= enforcementPoints?.let { tollService.TollCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.EXIT) }


        eventRepository.save(createEvent(transaction, toll, timestamp, EventType.END, enforcement_sample_size, enforcement_interceptions))
        return transactionRepository.save(transaction.apply { this.state =  TransactionState.AWAITING_CONFIRMATION})
    }

    fun isVehicleRestrictedByTransaction(vehicle: Vehicle): Boolean{
        val transaction = transactionRepository.findOneByVehicleOrderByCreatedDesc(vehicle)
        if(!transaction.isPresent) return true
        return with(transaction.get()){ state != TransactionState.INCOMPLETE}
    }

    //debug
    fun getAll(user: User): List<Event>{
        val res = eventRepository.findByOrderByTimestampDesc()
        return res
    }

}