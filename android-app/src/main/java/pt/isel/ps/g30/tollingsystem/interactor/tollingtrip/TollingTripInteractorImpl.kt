package pt.isel.ps.g30.tollingsystem.interactor.tollingtrip
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import pt.isel.ps.g30.tollingsystem.extension.toTollingTrip
import java.util.*

class TollingTripInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase) : TollingTripInteractor {


    override suspend fun getVehicleTripList(vehicleId: Int): Deferred<List<TollingTrip>>{
        return async { tollingSystemDatabase.TollingTripDao().findByVehicle(vehicleId) }

    }

    override  suspend fun getTollingTripList() : Deferred<List<TollingTrip>>{
        return async { tollingSystemDatabase.TollingTripDao().findAll() }
    }

    override suspend fun getTollingTrip(id: Int): Deferred<TollingTrip> {
        return async { tollingSystemDatabase.TollingTripDao().findById(id) ?: throw Exception("Could not find trip")}
    }

    override suspend fun startTollingTrip(origin: TollingPlaza): Deferred<ActiveTrip> {
        return async {

            val tollingTrip = tollingSystemDatabase.ActiveTripDao().find()
            tollingTrip.vehicle ?: throw Exception("Could not start trip, no active vehicle found")


            tollingTrip.origin = origin
            tollingTrip.destTimestamp =  Date()

            if(tollingSystemDatabase.ActiveTripDao().update(tollingTrip) ==  0)  throw Exception("Could not start trip")

            return@async tollingTrip
        }
    }

    override suspend fun finishTollingTrip(dest: TollingPlaza): Deferred<ActiveTrip> {
        return async {
            val activeTrip = tollingSystemDatabase.ActiveTripDao().find()

            if(activeTrip.origin == null || activeTrip.vehicle== null) throw Exception("Could not finish trip, no active trip found")


            activeTrip.destination = dest
            activeTrip.destTimestamp =  Calendar.getInstance().time

            val insertedId = tollingSystemDatabase.TollingTripDao().insert(activeTrip.toTollingTrip()).get(0)
            val insertedTollingTrip = tollingSystemDatabase.TollingTripDao().findById(insertedId.toInt())
                    ?: throw Exception("Could not finish trip")

            activeTrip.origin = null

            if(tollingSystemDatabase.ActiveTripDao().update(activeTrip) ==  0)  throw Exception("Could not finish trip")

            tollingSystemDatabase.NotificationDao().insert(Notification(NotificationType.TripNotification, trip = insertedTollingTrip))

            return@async activeTrip
        }
    }

    override suspend fun getActiveTollingTripLiveData(): Deferred<LiveData<ActiveTrip>> {
        return async { tollingSystemDatabase.ActiveTripDao().findLiveData() }
    }


    override suspend fun getActiveTollingTrip(): Deferred<ActiveTrip> {
        return async { tollingSystemDatabase.ActiveTripDao().find() }
    }


    override suspend fun cancelActiveTrip(trip: ActiveTrip): Deferred<Int>{
        return async { tollingSystemDatabase.ActiveTripDao().update(trip.apply { origin = null; originTimestamp = null }) }
    }
}
