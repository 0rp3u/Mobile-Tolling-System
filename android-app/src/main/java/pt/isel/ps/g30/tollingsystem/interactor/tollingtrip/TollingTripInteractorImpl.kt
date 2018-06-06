package pt.isel.ps.g30.tollingsystem.interactor.tollingtrip
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip

class TollingTripInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase) : TollingTripInteractor {


    override suspend fun getVehicleTripList(vehicleId: Int): Deferred<List<TollingTrip>>{
        return async { tollingSystemDatabase.TollingTripDao().findByVehicle(vehicleId) }

    }

    override  suspend fun getTollingTripList() : Deferred<List<TollingTrip>>{
        return async { tollingSystemDatabase.TollingTripDao().findAll() }
    }

    override suspend fun getTollingTrip(id: Int): Deferred<TollingTrip> {
       return async { tollingSystemDatabase.TollingTripDao().findById(id) }
    }

    override suspend fun startTollingTrip(origin: TollingPlaza): Deferred<TollingTrip> {
        return async {
            val vehicle = tollingSystemDatabase.VehicleDao().findActive() ?: throw Exception("Could not start trip, no active vehicle found")

            val tollingTrip = TollingTrip(vehicle, origin)
            if(tollingSystemDatabase.TollingTripDao().insert(tollingTrip).isEmpty()) throw Exception("Could not start trip")
            return@async tollingTrip
        }
    }

    override suspend fun finishTollingTrip(dest: TollingPlaza): Deferred<TollingTrip> {
        return async {
            val activeTrip = tollingSystemDatabase.TollingTripDao().findByActiveTrip()
                    ?: throw Exception("Could not finish trip, no active trip found")

            activeTrip.destination = dest

            tollingSystemDatabase.TollingTripDao().updateTrip(activeTrip)

            return@async activeTrip
        }
    }


    override suspend fun getActiveTollingTrip(): Deferred<TollingTrip?> {
       return async { tollingSystemDatabase.TollingTripDao().findByActiveTrip() }
    }

    override suspend fun cancelActiveTrip(trip: TollingTrip): Deferred<Int>{
        return async { tollingSystemDatabase.TollingTripDao().delete(trip) }
    }
}
