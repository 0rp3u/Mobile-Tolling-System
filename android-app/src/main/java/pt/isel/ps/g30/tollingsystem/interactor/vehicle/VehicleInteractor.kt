package pt.isel.ps.g30.tollingsystem.interactor.vehicle

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

interface VehicleInteractor : BaseInteractor {

    suspend fun getVehicleList() : Deferred<List<Vehicle>>

    suspend fun getVehicleListLiveData() : Deferred<LiveData<List<Vehicle>>>

    suspend fun getVehicle(id: Int) : Deferred<Vehicle>

    suspend fun getActiveVehicle() : Deferred<Vehicle?>

    suspend fun setActiveVehicle(vehicle: Vehicle): Job

    suspend fun removeActiveVehicle(vehicle: Vehicle): Deferred<Boolean> //TODO maybe not boolean

}
