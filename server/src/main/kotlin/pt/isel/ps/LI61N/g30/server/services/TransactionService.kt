package pt.isel.ps.LI61N.g30.server.services

import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.api.input.InputTransaction
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

    //TODO
    fun confirmTransaction(transaction_id: Long, new_begin_toll_id: Long, new_end_toll_id: Long, user: User){
        //Get Transaction
        val transaction = transactionRepository.findById(transaction_id).orElseThrow { Exception("Invalid transaction.") }
        if(transaction.state == TransactionState.AWAITING_CONFIRMATION) throw Exception("Invalid transaction.")
        if(transaction.vehicle.owner.id != user.id) throw Exception("Invalid transaction.")

        val tolls = tollRepository.findAllById(listOf(new_begin_toll_id, new_end_toll_id)).toList()
        val old_begin = transaction.event.first { it.tolltransactionId.type == EventType.BEGIN }
        val old_end = transaction.event.first { it.tolltransactionId.type == EventType.END }

        //Create new Amend
        TransactionAmendment(transaction = transaction, old_begin_toll = old_begin.toll.id, old_end_toll = old_end.toll.id).let {
            transaction.amendments.add(it)
        }

        //Change Transaction's events?

        transactionRepository.save(transaction.apply {

        })
    }

    fun getLatestTransaction(vehicle_id: Long, user: User): Transaction{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        return transactionRepository.findOneByVehicleOrderByCreatedDesc(vehicle).orElseThrow { Exception("No transaction found for vehicle.") }
    }

    fun getTransactions(date: Date?, user: User): List<Transaction>{
        return if(date != null){
            transactionRepository.findByUpdatedAfter(date).filter { it.vehicle.owner.id == user.id }
        }else{
            return transactionRepository.findAll().filter { it.vehicle.owner.id == user.id }
        }
    }

    fun getTransaction(transaction_id: Long, user: User): Transaction{
        transactionRepository.findById(transaction_id).orElseThrow { Exception("Invalid Transaction.") }.let {
            if(it.vehicle.owner.id != user.id) throw Exception("Invalid Transaction.")
            return it
        }
    }

    fun cancelTransaction(transaction_id: Long, user: User): Transaction{
        getTransaction(transaction_id, user).let {
            return transactionRepository.save(it.apply { this.state = TransactionState.CANCELED })
        }
    }

    //TODO
    fun create(inputTransaction: InputTransaction): Transaction{


        throw NotImplementedError()
    }

//    fun confirmTransaction(id: Long, user: User): Transaction{
//        transactionRepository.findById(id).orElseThrow { Exception("Invalid transaction.") }.let {
//            if(it.vehicle.owner.id != user.id)
//                throw Exception("Invalid user for this action")
//
//
//            //todo
//            throw NotImplementedError()
//        }
//    }

}