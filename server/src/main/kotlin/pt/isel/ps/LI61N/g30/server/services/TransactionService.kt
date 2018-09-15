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

    fun confirmTransaction(transaction_id: Long, inputTran: InputTransaction, user: User): Transaction{
        //Get Transaction
        val transaction = getTransaction(transaction_id, user)
        if(transaction.state != TransactionState.AWAITING_CONFIRMATION) throw Exception("Invalid transaction.")

        val tolls = tollRepository.findAllById(listOf(inputTran.begin_toll, inputTran.end_toll)).toList()
        if(tolls.size != 2) throw Exception("Invalid toll")

        val old_begin = transaction.event.first { it.tolltransactionId.type == EventType.BEGIN }
        val old_end = transaction.event.first { it.tolltransactionId.type == EventType.END }

        if(old_begin.toll.id != inputTran.begin_toll || old_end.toll.id != inputTran.end_toll){
            //Create new Amend
            TransactionAmendment(transaction = transaction, old_begin_toll = old_begin.toll.id, old_end_toll = old_end.toll.id).let {
                transaction.amendments.add(it)
            }
            //Change transaction events
            old_begin.toll = tolls[0]
            old_end.toll = tolls[1]
        }

        return transactionRepository.save(transaction.apply {
            state = TransactionState.CONFIRMED
        })
    }

    fun cancelTransaction(transaction_id: Long, user: User): Transaction{
        getTransaction(transaction_id, user).let {
            return transactionRepository.save(it.apply { this.state = TransactionState.CANCELED })
        }
    }

    fun create(inputTransaction: InputTransaction, user: User): Transaction{
        with(inputTransaction) {
            val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("Vehicle not found.") }
            val transaction = transactionRepository.save(Transaction(vehicle = vehicle))

            val begin_event = eventService.beginEvent(transaction, begin_toll, begin_timestamp, begin_geoLocations, user)

            val end_event = eventService.endEvent(transaction, end_toll, end_timestamp, end_geoLocations, user)

            return transactionRepository.save(transaction.apply {
                event.add(begin_event)
                event.add(end_event)
            })
        }
    }

    fun getLatestTransaction(vehicle_id: Long, user: User): Transaction{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        return transactionRepository.findTop1ByVehicleOrderByCreatedDesc(vehicle).orElseThrow { Exception("No transaction found for vehicle.") }
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