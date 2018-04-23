package pt.isel.ps.g30.tollingsystem.interactor.vehicle

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.interactor.base.BaseInteractor
import pt.isel.ps.g30.tollingsystem.model.Vehicle

interface VehicleInteractor : BaseInteractor {

    suspend fun getVehicleList() : Deferred<List<Vehicle>>

}
