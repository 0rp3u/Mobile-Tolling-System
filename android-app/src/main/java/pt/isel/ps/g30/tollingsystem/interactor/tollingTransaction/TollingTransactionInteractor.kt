package pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import pt.isel.ps.g30.tollingsystem.data.database.model.TemporaryTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPassage
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface TollingTransactionInteractor : BaseInteractor {

    suspend fun getVehicleTransactionList(vehicleId: Int): Deferred<List<TollingTransaction>>

    suspend fun getTollingTransactionList() : Deferred<List<TollingTransaction>>

    suspend fun getTollingTransaction(id: Int) : Deferred<TollingTransaction>

    suspend fun getCurrentTransactionLiveData(): Deferred<LiveData<TemporaryTransaction>>

    suspend fun getCurrentTransactionTransaction(): Deferred<TemporaryTransaction>

    suspend fun startTollingTransaction(origin: TollingPassage): Deferred<TemporaryTransaction>

    suspend fun finishTransaction(dest: TollingPassage): Job

    suspend fun cancelCurrentTransaction(transaction: TemporaryTransaction): Deferred<Int>

}
