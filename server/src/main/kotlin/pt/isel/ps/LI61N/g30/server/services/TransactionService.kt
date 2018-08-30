package pt.isel.ps.LI61N.g30.server.services

import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.logic.gis.CountPointsWithinPolygon
import pt.isel.ps.LI61N.g30.server.model.domain.*
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.*
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*

@Service
class TransactionService(
        val transactionRepository: TransactionRepository,
        val tollRepository: TollRepository,
        val eventService: EventService,
        val vehicleRepository: VehicleRepository,
        val eventRepository: EventRepository,
        val tollService: TollService,
        val transactionAmendmentRepository: TransactionAmendmentRepository
){

    fun amendTransaction(transaction_id: Long, new_begin_toll_id: Long, new_end_toll_id: Long, user: User){
        //Get Transaction
        val transaction = transactionRepository.findById(transaction_id).orElseThrow { Exception("Invalid transaction.") }
        if(transaction.state == TransactionState.AWAITING_CONFIRMATION) throw Exception("Invalid transaction.")
        if(transaction.vehicle.owner.id != user.id) throw Exception("Invalid transaction.")

        val tolls = tollRepository.findAllById(listOf(new_begin_toll_id, new_end_toll_id)).toList()
        val old_begin = transaction.event.first { it.tolltransactionId.type == EventType.BEGIN }
        val old_end = transaction.event.first { it.tolltransactionId.type == EventType.END }
        //Create new Amend
        TransactionAmendment(transaction = transaction, old_begin_toll = old_begin.toll.id, old_end_toll = old_end.toll.id, new_begin_toll = tolls[0].id, new_end_toll = tolls[1].id).let {
            transaction.amendments.add(it)
        }

        //Change Transaction's events?


        transactionRepository.save(transaction)
    }

    fun getLatestTransaction(vehicle_id: Long, user: User): Transaction{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        return transactionRepository.findOneByVehicleOrderByCreatedDesc(vehicle).orElseThrow { Exception("No transaction found for vehicle.") }
    }

    fun getTransactions(user: User): List<Transaction>{
        return transactionRepository.findAll().filter { it.vehicle.owner.id == user.id }
    }

}