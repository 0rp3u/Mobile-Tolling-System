package pt.isel.ps.g30.tollingsystem.interactor.geofencing

import kotlinx.coroutines.experimental.Job
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface GeofencingInteractor : BaseInteractor {

    suspend fun getActiveGeofences() : Job

    suspend fun registerGeofences() : Job

    suspend fun removeRegisteredGeofences(): Job

}
