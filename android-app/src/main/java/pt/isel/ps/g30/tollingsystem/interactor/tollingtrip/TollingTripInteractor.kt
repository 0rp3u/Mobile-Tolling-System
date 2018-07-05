package pt.isel.ps.g30.tollingsystem.interactor.tollingtrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.database.model.ActiveTrip
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface TollingTripInteractor : BaseInteractor {

    suspend fun getVehicleTripList(vehicleId: Int): Deferred<List<TollingTrip>>

    suspend fun getTollingTripList() : Deferred<List<TollingTrip>>

    suspend fun getTollingTrip(id: Int) : Deferred<TollingTrip>

    suspend fun getActiveTollingTripLiveData(): Deferred<LiveData<ActiveTrip>>

    suspend fun getActiveTollingTrip(): Deferred<ActiveTrip>

    suspend fun startTollingTrip(origin: TollingPlaza): Deferred<ActiveTrip>

    suspend fun finishTollingTrip(dest: TollingPlaza): Deferred<TollingTrip>

    suspend fun cancelActiveTrip(trip: ActiveTrip): Deferred<Int>

}
