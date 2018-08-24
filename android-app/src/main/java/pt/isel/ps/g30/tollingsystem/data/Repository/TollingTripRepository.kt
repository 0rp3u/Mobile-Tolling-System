package pt.isel.ps.g30.tollingsystem.data.Repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.extension.toFInishedTransaction
import pt.isel.ps.g30.tollingsystem.utils.NetworkUtils
import java.util.*

class TollingTripRepository(private val tollingSystemDatabase: TollingSystemDatabase, private val apiService: TollingService, private val networkUtils: NetworkUtils ){


    fun getVehicleTripList(vehicleId: Int): Deferred<List<pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTripDao().findByVehicle(vehicleId) }

    }

    fun getTollingTripList() : Deferred<List<pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTripDao().findAll() }
    }

    fun getTollingTrip(id: Int): Deferred<pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction> {
        return async { tollingSystemDatabase.TollingTripDao().findById(id) ?: throw Exception("Could not find transaction")}
    }

    fun startTollingTrip(origin: TollingPlaza): Deferred<CurrentTransaction> {
        return async {

            val tollingTrip = tollingSystemDatabase.ActiveTripDao().find()
            tollingTrip.vehicle ?: throw Exception("Could not start transaction, no active vehicle found")


            tollingTrip.origin = origin
            tollingTrip.destTimestamp =  Date()

            if(tollingSystemDatabase.ActiveTripDao().update(tollingTrip) ==  0)  throw Exception("Could not start transaction")

            if(networkUtils.isConnected()) launch{apiService.initiateTollingTransaction(TollingTransaction(tollingTrip.id, tollingTrip.originTimestamp!!, tollingTrip.vehicle?.id!!, tollingTrip.origin?.id!!)).await()}

            return@async tollingTrip
        }
    }

    suspend fun finishTollingTrip(dest: TollingPlaza): Deferred<CurrentTransaction> {
        return async {
            val activeTrip = tollingSystemDatabase.ActiveTripDao().find()

            if(activeTrip.origin == null || activeTrip.vehicle== null) throw Exception("Could not finish transaction, no active transaction found")


            activeTrip.destination = dest
            activeTrip.destTimestamp =  Calendar.getInstance().time

            val insertedId = tollingSystemDatabase.TollingTripDao().insert(activeTrip.toFInishedTransaction())[0]
            val insertedTollingTrip = tollingSystemDatabase.TollingTripDao().findById(insertedId.toInt())
                    ?: throw Exception("Could not finish transaction")

            activeTrip.origin = null

            if(tollingSystemDatabase.ActiveTripDao().update(activeTrip) ==  0)  throw Exception("Could not finish transaction")

            if(networkUtils.isConnected()) launch{apiService.closeTollingTransaction(TollingTransaction(insertedTollingTrip.id, insertedTollingTrip.originTimestamp, insertedTollingTrip.vehicle.id, insertedTollingTrip.origin.id, insertedTollingTrip.destination.id)).await()}

            return@async activeTrip
        }
    }

    fun getActiveTollingTripLiveData(): Deferred<LiveData<CurrentTransaction>> {
        return async { tollingSystemDatabase.ActiveTripDao().findLiveData() }
    }


    fun getActiveTollingTrip(): Deferred<CurrentTransaction> {
        return async { tollingSystemDatabase.ActiveTripDao().find() }
    }

    fun cancelActiveTrip(trip: CurrentTransaction): Deferred<Int>{
        return async {
            if(networkUtils.isConnected()) {
                apiService.cancelTollingTransaction(TollingTransaction(trip.id, trip.originTimestamp!!, trip.vehicle?.id!!, trip.origin?.id!!, trip.destination?.id)).await()

                tollingSystemDatabase.ActiveTripDao().update(trip.apply { origin = null; originTimestamp = null })
            }else
                throw Exception("Not connected to the internet, you need to be connected")
        }
    }
}