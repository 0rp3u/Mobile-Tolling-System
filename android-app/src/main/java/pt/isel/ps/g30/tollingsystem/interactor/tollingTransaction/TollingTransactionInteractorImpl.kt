package pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction
import androidx.lifecycle.LiveData
import androidx.work.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.services.work.CreateTransactionWork
import java.util.*

class TollingTransactionInteractorImpl(
        private val tollingSystemDatabase: TollingSystemDatabase,
        private val notificationInteractor: NotificationInteractor
) : TollingTransactionInteractor {


    override suspend fun getVehicleTransactionList(vehicleId: Int): Deferred<List<TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTransactionDao().findByVehicle(vehicleId) }

    }

    override  suspend fun getTollingTransactionList() : Deferred<List<TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTransactionDao().findAll() }
    }

    override suspend fun getTollingTransaction(id: Int): Deferred<TollingTransaction> {
        return async { tollingSystemDatabase.TollingTransactionDao().findById(id) ?: throw Exception("Could not findClean transaction")}
    }

    override suspend fun startTollingTransaction(origin: TollingPassage): Deferred<TemporaryTransaction> {
        return async {

            val tollingTransaction = getCurrentTransactionTransaction().await()
            tollingTransaction.vehicle ?: throw Exception("Could not start transaction, no active vehicle found")

            tollingTransaction.origin = origin
            origin.TransactionId = tollingTransaction.id

            if(tollingSystemDatabase.ActiveTransactionDao().update(tollingTransaction) ==  0)  throw Exception("Could not start transaction")


            if(tollingSystemDatabase.TollingPassageDao().update(origin) ==  0)  throw Exception("Could not update transaction")

            notificationInteractor.sendStartTransactionNotification(tollingTransaction)

            if(origin.plaza.openToll) finishTransaction(origin)

            return@async tollingTransaction
        }
    }

    override suspend fun finishTransaction(dest: TollingPassage) = launch {
            val activeTransaction =  getCurrentTransactionTransaction().await()

            if(activeTransaction.origin == null || activeTransaction.vehicle== null) throw Exception("Could not finish transaction, no active transaction found")


            activeTransaction.destination = dest
            dest.TransactionId = activeTransaction.id
            activeTransaction.clean = false

        if(tollingSystemDatabase.ActiveTransactionDao().update(activeTransaction) ==  0)  throw Exception("Could not start transaction")

        if(tollingSystemDatabase.TollingPassageDao().update(dest) ==  0)  throw Exception("Could not update transaction")


        val workManager : WorkManager = WorkManager.getInstance()
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            val request = OneTimeWorkRequestBuilder<CreateTransactionWork>()
                    .setConstraints(constraints)
                    .addTag(CreateTransactionWork.TAG)
                    .setInputData(Data.Builder().putInt(CreateTransactionWork.KEY_ID, activeTransaction.id).build())
                    .build()
            workManager.enqueue(request)




            if(tollingSystemDatabase.ActiveTransactionDao().update(activeTransaction) ==  0)  throw Exception("Could not finish transaction")
    }

    override suspend fun getCurrentTransactionLiveData(): Deferred<LiveData<TemporaryTransaction>> {
        return async { tollingSystemDatabase.ActiveTransactionDao().findCleanData() }
    }


    override suspend fun getCurrentTransactionTransaction(): Deferred<TemporaryTransaction> {
        return async { tollingSystemDatabase.ActiveTransactionDao().findClean() ?: TemporaryTransaction().also {  tollingSystemDatabase.ActiveTransactionDao().insert(it)}
        }
    }


    override suspend fun cancelCurrentTransaction(transaction: TemporaryTransaction): Deferred<Int>{
        return async { tollingSystemDatabase.ActiveTransactionDao().delete(transaction) }
    }
}
