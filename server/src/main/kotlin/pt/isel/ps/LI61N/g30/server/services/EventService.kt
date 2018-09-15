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
import pt.isel.ps.LI61N.g30.server.utils.TollPassageInfo

@Service
class EventService(
        private val eventRepository: EventRepository,
        private val vehicleRepository: VehicleRepository,
        private val tollRepository: TollRepository,
        private val transactionRepository: TransactionRepository,
        private val tollService: TollService
) {

    fun beginEvent(transaction: Transaction, toll_id: Long, timestamp: Date, enforcementPoints: Array<TollPassageInfo>?, user: User): Event{
        //check if the same vehicle is not already in an uncompleted event
        if(isVehicleRestrictedByTransaction(transaction.vehicle)) throw Exception("Vehicle is not eligible for this action.")
        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Invalid Toll.") }

        if( !isVehicleFromOwner(transaction.vehicle, user))
            throw Exception("Vehicle does not belong to that Owner.")

        //Toll Enforcement check
        val enforcement_sample_size = enforcementPoints?.size
        val enforcement_interceptions= enforcementPoints?.let { tollService.TollCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.ENTRY) }

        return eventRepository.save(createEvent(transaction, toll, timestamp, EventType.BEGIN, enforcement_sample_size, enforcement_interceptions))
    }

    fun endEvent(transaction: Transaction, toll_id: Long, timestamp: Date, enforcementPoints: Array<TollPassageInfo>?, user: User): Event{

        /*Validation*/
        if(transaction.state != TransactionState.INCOMPLETE)
            throw Exception("The operation for this transaction is invalid.")

        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Toll does not exist.") }

        val enforcement_sample_size = enforcementPoints?.size
        val enforcement_interceptions= enforcementPoints?.let { tollService.TollCheck(enforcementPoints, toll, CountPointsWithinPolygon.Area.EXIT) }

        return eventRepository.save(createEvent(transaction, toll, timestamp, EventType.END, enforcement_sample_size, enforcement_interceptions)).apply {
            transactionRepository.save(transaction.apply { this.state =  TransactionState.AWAITING_CONFIRMATION})
        }
        //return transactionRepository.save(transaction.apply { this.state =  TransactionState.AWAITING_CONFIRMATION})
    }

    fun isVehicleRestrictedByTransaction(vehicle: Vehicle): Boolean{
        val transaction = transactionRepository.findTop1ByVehicleOrderByCreatedDesc(vehicle)
        if(!transaction.isPresent) return true
        return with(transaction.get()){ state != TransactionState.INCOMPLETE}
    }

    //debug
    fun getAll(user: User): List<Event>{
        val res = eventRepository.findByOrderByTimestampDesc()
        return res
    }

}