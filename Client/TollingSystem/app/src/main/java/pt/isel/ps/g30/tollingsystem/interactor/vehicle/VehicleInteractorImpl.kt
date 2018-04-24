package pt.isel.ps.g30.tollingsystem.interactor.vehicle
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import pt.isel.ps.g30.tollingsystem.api.TollingService
import pt.isel.ps.g30.tollingsystem.model.Tare

class VehicleInteractorImpl(private val tollingService: TollingService) : VehicleInteractor {

    override  suspend fun getVehicleList() : Deferred<List<Vehicle>>{
        val deferred = CompletableDeferred<List<Vehicle>>()

        deferred.complete(
        listOf(
                Vehicle(1, "14-AR-43", "david",Tare.Classe_1),
                Vehicle(2, "44-EW-89", "david",Tare.Classe_2)
        ))
        //return tollingService.getVehicleList("me")


        return deferred
    }
}
