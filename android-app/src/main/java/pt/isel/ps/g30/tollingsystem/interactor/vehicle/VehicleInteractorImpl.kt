package pt.isel.ps.g30.tollingsystem.interactor.vehicle
import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractorImpl

class VehicleInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase, private val tollingTransactionInteractor: TollingTransactionInteractor) : VehicleInteractor {

    override  suspend fun getVehicleList() : Deferred<List<Vehicle>>{
        return async { tollingSystemDatabase.VehicleDao().findAll() }
    }

    override  suspend fun getVehicleListLiveData() : Deferred<LiveData<List<Vehicle>>>{
        return async { tollingSystemDatabase.VehicleDao().findAllLiveData() }
    }

    override suspend fun getVehicle(id: Int): Deferred<Vehicle> {
        return async { tollingSystemDatabase.VehicleDao().findById(id) }
    }

    override suspend fun getActiveVehicle(): Deferred<Vehicle?> {
        return async { tollingSystemDatabase.VehicleDao().findActive() }
    }

    override suspend fun setActiveVehicle(vehicle: Vehicle) = launch {
        val act = tollingTransactionInteractor.getCurrentTransactionTransaction().await()
        if(act.vehicle != null) throw Exception("a vehicle is already active")
        act.vehicle = vehicle

        tollingSystemDatabase.ActiveTransactionDao().update(act)
    }

    override suspend fun removeActiveVehicle(vehicle: Vehicle): Deferred<Boolean>{
        return async {
            tollingTransactionInteractor.getCurrentTransactionTransaction().await().let {

                it.vehicle = null
                tollingSystemDatabase.ActiveTransactionDao().update(it)

            }
            true
        }
    }
}
