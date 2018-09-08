package pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import pt.isel.ps.g30.tollingsystem.data.database.model.UnvalidatedTransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPassage
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface TollingTransactionInteractor : BaseInteractor {

    suspend fun getVehicleTransactionList(vehicleId: Int): Deferred<List<TollingTransaction>>

    suspend fun getTollingTransactionList() : Deferred<List<TollingTransaction>>

    suspend fun getTollingTransaction(id: Int) : Deferred<TollingTransaction>

    suspend fun getCurrentTransactionLiveData(): Deferred<LiveData<UnvalidatedTransactionInfo>>

    suspend fun getCurrentTransactionTransaction(): Deferred<UnvalidatedTransactionInfo>

    suspend fun startTollingTransaction(origin: TollingPassage): Deferred<UnvalidatedTransactionInfo>

    suspend fun finishTransaction(dest: TollingPassage): Job

    suspend fun cancelCurrentTransaction(transaction: UnvalidatedTransactionInfo): Deferred<Int>

}
