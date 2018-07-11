package pt.isel.ps.LI61N.g30.server.services

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.*
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TollRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TransactionRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TripRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository
import pt.isel.ps.LI61N.g30.server.api.controllers.input.InputTransaction
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromOwner
import pt.isel.ps.LI61N.g30.server.services.VehicleService.Companion.isVehicleFromTrip
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*
import javax.persistence.EntityManager
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.model.domain.TollTripId
import pt.isel.ps.LI61N.g30.server.model.domain.Trip

@Service
class TransactionService(
        private val transactionRepository: TransactionRepository,
        private val vehicleRepository: VehicleRepository,
        private val tollRepository: TollRepository,
        private val tripRepository: TripRepository,
        val entityManager: EntityManager
) {

    val checkPointsWithinPolygonQuery =
            "SELECT count(*) filter(\n" +
                    "\twhere ST_Intersects(\n" +
                    "\t\t_point,\n" +
                    "\t\t(select :area from mts_toll where id = :toll_id)\n" +
                    "\t)\n" +
                    ")\n" +
                    "FROM (\n" +
                    "\tselect _point\n" +
                    "\tfrom (\n" +
                    "\t\tVALUES\n" +
                    "\t\t\t:points\n" +
                    "\t)\n" +
                    "\tas lookup(_point)\n" +
                    ") as temp"


    val ENTRY_AREA = "entry_area"
    val EXIT_AREA = "exit_area"

    fun TollEnforcementCheck(geoLocations: Array<GeoLocation>, toll: Toll, area: String) : Int{
        val query = entityManager.createNativeQuery(checkPointsWithinPolygonQuery)
                .setParameter("toll_id", toll.id)
                .setParameter("points", generateQueryPoints(geoLocations))
                .singleResult as Int
        return query
    }

    private fun generateQueryPoints(geoLocations: Array<GeoLocation>) =
        geoLocations.joinToString { "(ST_SetSRID(ST_MakePoint(${it.longitude},${it.latitude}), 4326))," }.dropLast(1)


    fun beginTransaction(vehicle_id: Long, toll_id: Long, timestamp: String, geoLocation: Array<GeoLocation>?, user: User): Transaction{
        //check if the same vehicle is not already in an uncompleted transaction
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        if(isVehicleRestrictedByTrip(vehicle)) throw Exception("Vehicle is not eligible for this action.")

        val trip = Trip(vehicle = vehicle)
        val toll = tollRepository.findById(toll_id).get()

        //Toll Enforcement check
        val sucess_ratio = if(geoLocation != null) TollEnforcementCheck(geoLocation, toll, ENTRY_AREA) else -1

        return transactionRepository.save(createTransaction(trip, toll, Date(timestamp), TransactionType.BEGIN))
    }

    fun endTransaction(vehicle_id: Long, toll_id: Long, timestamp: String, trip_id: Long, user: User): Transaction{
        //Validation
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        val trip = tripRepository.findById(trip_id).orElseThrow { Exception("Trip does not exist.") }
        if( !isVehicleFromOwner(vehicle, user))
            throw Exception("Vehicle does not belong to that Owner.")

        if(!isVehicleFromTrip(trip, vehicle))
            throw Exception("Vehicle does nto belong to that trip.")

        val toll = tollRepository.findById(toll_id).orElseThrow { Exception("Toll does not exist.") }

        //Toll Enforcement check
        //todo val sucess_ratio = TollEnforcementCheck(geoLocation, toll, EXIT_AREA)

        return transactionRepository.save(createTransaction(trip, toll, Date(timestamp), TransactionType.END)).also {
            tripRepository.save(trip.apply { this.state =  "complete"})
        }
    }

    fun isVehicleRestrictedByTrip(vehicle: Vehicle): Boolean{
        tripRepository.findOneByVehicleOrderByCreatedDesc(vehicle).let {
            return it.isPresent && it.get().state == "completed"
        }
    }

    data class RegisterTransactionInputModel(
            val vehicle: Long,
            val toll_begin: Long,
            val timestamp_begin: String,
            val toll_end: Long,
            val timestamp_end: String
    )

    private fun createTransaction(trip: Trip, toll: Toll, timestamp: Date, type: TransactionType): Transaction{
        return Transaction(
                tolltripId = TollTripId(trip_id = trip.id, toll_id = toll.id, type = type),
                toll = toll,
                trip = trip,
                timestamp = timestamp
        )
    }

    fun registerTrip(input: InputTransaction){
        val trips = tripRepository.findAll()
        val tolls = tollRepository.findAll()
        val transactions = transactionRepository.findAll()

        val t = transactionRepository.findByOrderByTimestampDesc(PageRequest.of(0, 1))[0]

        try {
            val n1 = transactionRepository.save(createTransaction(t.trip, t.toll, t.timestamp.apply { month = 7 }, TransactionType.BEGIN))
            val n2= transactionRepository.save(createTransaction(t.trip, t.toll, t.timestamp.apply { month = 8 }, TransactionType.BEGIN))
            val n3= transactionRepository.save(createTransaction(t.trip, t.toll, t.timestamp.apply { month = 9 }, TransactionType.BEGIN))
            val n4 = -1
        }catch ( e: Throwable){
            val nop = -1
        }
        val i = -1
        //val t1 = createTransaction()
        //transactionRepository.save(t1)
    }

//    fun findAllByVehicle(vehicle_id: Long): List<Transaction>{
//        val vehicle = vehicleRepository.findById(vehicle_id).orElseThrow { Exception() }
//        val list = transactionRepository.findAllByVehicle(vehicle)
//        return list
//    }

    fun getLatestTrip(vehicle_id: Long, user: User): Trip{
        val vehicle = vehicleRepository.findByOwnerAndId(user, vehicle_id).orElseThrow { Exception("No vehicle found with the provided id.") }
        val trip = tripRepository.findOneByVehicleOrderByCreatedDesc(vehicle).orElseThrow { Exception("No trip found for vehicle.") }
        return trip
    }

    fun getAll(user: User): List<Transaction>{
        val res = transactionRepository.findByOrderByTimestampDesc()
        return res
    }

}