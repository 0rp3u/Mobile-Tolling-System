package pt.isel.ps.LI61N.g30.server.services

import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.*
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TollRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TripAmendmentRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TripRepository

@Service
class TripService(
        val tripRepository: TripRepository,
        val tollRepository: TollRepository,
        val tripAmendmentRepository: TripAmendmentRepository
){

    fun amendTrip(trip_id: Long, new_begin_toll_id: Long, new_end_toll_id: Long, user: User){
        //Get Trip
        val trip = tripRepository.findById(trip_id).orElseThrow { Exception("Invalid trip.") }
        if(trip.state == TripState.AWAITING_CONFIRMATION) throw Exception("Invalid trip.")
        if(trip.vehicle.owner.id != user.id) throw Exception("Invalid trip.")

        val tolls = tollRepository.findAllById(listOf(new_begin_toll_id, new_end_toll_id)).toList()
        val old_begin = trip.transaction.first { it.tolltripId.type == TransactionType.BEGIN }
        val old_end = trip.transaction.first { it.tolltripId.type == TransactionType.END }
        //Create new Amend
        TripAmendment(trip = trip, old_begin_toll = old_begin.toll.id, old_end_toll = old_end.toll.id, new_begin_toll = tolls[0].id, new_end_toll = tolls[1].id).let {
            trip.amendments.add(it)
        }

        //Change Trip's transactions?


        tripRepository.save(trip)
    }

}