package pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction
import androidx.lifecycle.LiveData
import androidx.work.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.services.work.CreateTransactionWork

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
        return async { tollingSystemDatabase.TollingTransactionDao().findById(id) ?: throw Exception("Could not findToClose transaction")}
    }

    override suspend fun startTollingTransaction(origin: TollingPassage): Deferred<UnvalidatedTransactionInfo> {
        return async {

            val tollingTransaction = getCurrentTransactionTransaction().await()
            tollingTransaction.vehicle ?: throw Exception("Could not start transaction, no active vehicle found")

            tollingTransaction.origin = origin
            origin.TransactionId = tollingTransaction.id


            if(origin.id == 0) tollingSystemDatabase.TollingPassageDao().insert(origin)
            else if(tollingSystemDatabase.TollingPassageDao().update(origin) ==  0)  throw Exception("Could not update transaction")


            if(tollingSystemDatabase.ActiveTransactionDao().update(tollingTransaction) ==  0)  throw Exception("Could not start transaction")

            notificationInteractor.sendStartTransactionNotification(tollingTransaction)

            if(origin.plaza.openToll){
                finishTransaction(origin).join()
            }

            return@async tollingTransaction
        }
    }

    override suspend fun finishTransaction(dest: TollingPassage) = launch {
        val activeTransaction =  getCurrentTransactionTransaction().await()


        if(activeTransaction.origin == null || activeTransaction.vehicle== null) throw Exception("Could not finish transaction, no active transaction found")


        activeTransaction.destination = dest
        activeTransaction.closed = true
        dest.TransactionId = activeTransaction.id

        if(dest.id == 0) tollingSystemDatabase.TollingPassageDao().insert(dest)
        else if(tollingSystemDatabase.TollingPassageDao().update(dest) ==  0)  throw Exception("Could not update transaction")


        if(tollingSystemDatabase.ActiveTransactionDao().update(activeTransaction) ==  0)  throw Exception("Could not start transaction")

        tollingSystemDatabase.ActiveTransactionDao().insert(UnvalidatedTransactionInfo(activeTransaction.userId, activeTransaction.vehicle))

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

    }

    override suspend fun getCurrentTransactionLiveData(): Deferred<LiveData<UnvalidatedTransactionInfo>> {
        return async { tollingSystemDatabase.ActiveTransactionDao().findToCloseData() }
    }


    override suspend fun getCurrentTransactionTransaction(): Deferred<UnvalidatedTransactionInfo> {
        return async {
            tollingSystemDatabase.ActiveTransactionDao().findToClose() ?:
            UnvalidatedTransactionInfo(tollingSystemDatabase.UserDao().findCurrent()!!.id)
                    .let { tollingSystemDatabase.ActiveTransactionDao().insert(it)}
                    .let { tollingSystemDatabase.ActiveTransactionDao().find(it.toInt())!!}
        }
    }


    override suspend fun cancelCurrentTransaction(transaction: UnvalidatedTransactionInfo): Deferred<Int>{
        return async {
            if(transaction.vehicle != null){
                transaction.origin = null
            }else{
                transaction.origin = null
                transaction.vehicle = null
            }
            tollingSystemDatabase.ActiveTransactionDao().update(transaction)
        }
    }
}
