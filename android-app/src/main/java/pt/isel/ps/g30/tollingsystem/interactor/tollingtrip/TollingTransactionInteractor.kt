package pt.isel.ps.g30.tollingsystem.interactor.tollingtrip

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface TollingTransactionInteractor : BaseInteractor {

    suspend fun getVehicleTransactionList(vehicleId: Int): Deferred<List<TollingTransaction>>

    suspend fun getTollingTransactionList() : Deferred<List<TollingTransaction>>

    suspend fun getTollingTransaction(id: Int) : Deferred<TollingTransaction>

    suspend fun getCurrentTransactionLiveData(): Deferred<LiveData<CurrentTransaction>>

    suspend fun getCurrentTransactionTrip(): Deferred<CurrentTransaction>

    suspend fun startTollingTransaction(origin: TollingPlaza): Deferred<CurrentTransaction>

    suspend fun finishTransaction(dest: TollingPlaza): Deferred<TollingTransaction>

    suspend fun cancelCurrentTransaction(trip: CurrentTransaction): Deferred<Int>

}
