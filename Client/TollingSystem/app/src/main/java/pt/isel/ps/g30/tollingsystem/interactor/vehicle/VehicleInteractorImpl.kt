package pt.isel.ps.g30.tollingsystem.interactor.vehicle
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import pt.isel.ps.g30.tollingsystem.api.TollingService

class VehicleInteractorImpl(private val tollingService: TollingService) : VehicleInteractor {

    override  suspend fun getVehicleList() : Deferred<List<Vehicle>>{
        return tollingService.getVehicleList("me")
    }
}
