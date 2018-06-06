package pt.isel.ps.g30.tollingsystem.interactor.vehicle
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase

class VehicleInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase) : VehicleInteractor {

    override  suspend fun getVehicleList() : Deferred<List<Vehicle>>{
        return async { tollingSystemDatabase.VehicleDao().findAll() }
    }

    override suspend fun getVehicle(id: Int): Deferred<Vehicle> {
        return async { tollingSystemDatabase.VehicleDao().findById(id) }
    }

    override suspend fun getActiveVehicle(): Deferred<Vehicle?> {
        return async { tollingSystemDatabase.VehicleDao().findActive() }
    }

    override suspend fun setActiveVehicle(vehicle: Vehicle): Job {
        return async {
            if(tollingSystemDatabase.VehicleDao().findActive() != null) throw Exception("a vehicle is already active")
            vehicle.active = true
            tollingSystemDatabase.VehicleDao().setActive(vehicle)
            true
        }
    }

    override suspend fun removeActiveVehicle(vehicle: Vehicle): Deferred<Boolean>{
       return async {
            vehicle.active = false
            tollingSystemDatabase.VehicleDao().removeActive(vehicle)
            true
        }
    }
}
