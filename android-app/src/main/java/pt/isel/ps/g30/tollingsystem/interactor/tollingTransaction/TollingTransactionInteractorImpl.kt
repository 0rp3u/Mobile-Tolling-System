package pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction
import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import pt.isel.ps.g30.tollingsystem.extension.toFinishedTransaction
import java.util.*

class TollingTransactionInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase) : TollingTransactionInteractor {


    override suspend fun getVehicleTransactionList(vehicleId: Int): Deferred<List<TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTransactionDao().findByVehicle(vehicleId) }

    }

    override  suspend fun getTollingTransactionList() : Deferred<List<TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTransactionDao().findAll() }
    }

    override suspend fun getTollingTransaction(id: Int): Deferred<TollingTransaction> {
        return async { tollingSystemDatabase.TollingTransactionDao().findById(id) ?: throw Exception("Could not find transaction")}
    }

    override suspend fun startTollingTransaction(origin: TollingPlaza): Deferred<CurrentTransaction> {
        return async {

            val tollingTransaction = tollingSystemDatabase.ActiveTransactionDao().find()
            tollingTransaction.vehicle ?: throw Exception("Could not start transaction, no active vehicle found")


            tollingTransaction.origin = origin
            tollingTransaction.destTimestamp =  Date()

            if(tollingSystemDatabase.ActiveTransactionDao().update(tollingTransaction) ==  0)  throw Exception("Could not start transaction")

            if(origin.openToll) finishTransaction(origin).await()

            return@async tollingTransaction
        }
    }

    override suspend fun finishTransaction(dest: TollingPlaza): Deferred<TollingTransaction> {
        return async {
            val activeTransaction = tollingSystemDatabase.ActiveTransactionDao().find()

            if(activeTransaction.origin == null || activeTransaction.vehicle== null) throw Exception("Could not finish transaction, no active transaction found")


            activeTransaction.destination = dest
            activeTransaction.destTimestamp =  Calendar.getInstance().time

            val insertedId = tollingSystemDatabase.TollingTransactionDao().insert(activeTransaction.toFinishedTransaction()).get(0)
            val insertedTollingTransaction = tollingSystemDatabase.TollingTransactionDao().findById(insertedId.toInt())
                    ?: throw Exception("Could not finish transaction")

            activeTransaction.origin = null

            if(tollingSystemDatabase.ActiveTransactionDao().update(activeTransaction) ==  0)  throw Exception("Could not finish transaction")

            tollingSystemDatabase.NotificationDao().insert(Notification(NotificationType.TransactionNotification, transaction = insertedTollingTransaction))

            return@async insertedTollingTransaction
        }
    }

    override suspend fun getCurrentTransactionLiveData(): Deferred<LiveData<CurrentTransaction>> {
        return async { tollingSystemDatabase.ActiveTransactionDao().findLiveData() }
    }


    override suspend fun getCurrentTransactionTransaction(): Deferred<CurrentTransaction> {
        return async { tollingSystemDatabase.ActiveTransactionDao().find() }
    }


    override suspend fun cancelCurrentTransaction(Transaction: CurrentTransaction): Deferred<Int>{
        return async { tollingSystemDatabase.ActiveTransactionDao().update(Transaction.apply { origin = null; originTimestamp = null }) }
    }
}
