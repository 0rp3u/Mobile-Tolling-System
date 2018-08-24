package pt.isel.ps.g30.tollingsystem.interactor.tollingtrip
import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import pt.isel.ps.g30.tollingsystem.extension.toFInishedTransaction
import java.util.*

class TollingTransactionInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase) : TollingTransactionInteractor {


    override suspend fun getVehicleTransactionList(vehicleId: Int): Deferred<List<TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTripDao().findByVehicle(vehicleId) }

    }

    override  suspend fun getTollingTransactionList() : Deferred<List<TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTripDao().findAll() }
    }

    override suspend fun getTollingTransaction(id: Int): Deferred<TollingTransaction> {
        return async { tollingSystemDatabase.TollingTripDao().findById(id) ?: throw Exception("Could not find transaction")}
    }

    override suspend fun startTollingTransaction(origin: TollingPlaza): Deferred<CurrentTransaction> {
        return async {

            val tollingTrip = tollingSystemDatabase.ActiveTripDao().find()
            tollingTrip.vehicle ?: throw Exception("Could not start transaction, no active vehicle found")


            tollingTrip.origin = origin
            tollingTrip.destTimestamp =  Date()

            if(tollingSystemDatabase.ActiveTripDao().update(tollingTrip) ==  0)  throw Exception("Could not start transaction")

            if(origin.openToll) finishTransaction(origin).await()

            return@async tollingTrip
        }
    }

    override suspend fun finishTransaction(dest: TollingPlaza): Deferred<TollingTransaction> {
        return async {
            val activeTrip = tollingSystemDatabase.ActiveTripDao().find()

            if(activeTrip.origin == null || activeTrip.vehicle== null) throw Exception("Could not finish transaction, no active transaction found")


            activeTrip.destination = dest
            activeTrip.destTimestamp =  Calendar.getInstance().time

            val insertedId = tollingSystemDatabase.TollingTripDao().insert(activeTrip.toFInishedTransaction()).get(0)
            val insertedTollingTrip = tollingSystemDatabase.TollingTripDao().findById(insertedId.toInt())
                    ?: throw Exception("Could not finish transaction")

            activeTrip.origin = null

            if(tollingSystemDatabase.ActiveTripDao().update(activeTrip) ==  0)  throw Exception("Could not finish transaction")

            tollingSystemDatabase.NotificationDao().insert(Notification(NotificationType.TripNotification, transaction = insertedTollingTrip))

            return@async insertedTollingTrip
        }
    }

    override suspend fun getCurrentTransactionLiveData(): Deferred<LiveData<CurrentTransaction>> {
        return async { tollingSystemDatabase.ActiveTripDao().findLiveData() }
    }


    override suspend fun getCurrentTransactionTrip(): Deferred<CurrentTransaction> {
        return async { tollingSystemDatabase.ActiveTripDao().find() }
    }


    override suspend fun cancelCurrentTransaction(trip: CurrentTransaction): Deferred<Int>{
        return async { tollingSystemDatabase.ActiveTripDao().update(trip.apply { origin = null; originTimestamp = null }) }
    }
}
