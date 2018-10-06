package pt.isel.ps.g30.tollingsystem.services.work

import android.util.Log
import androidx.work.Worker
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.data.database.model.NotificationType
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.data.mapForApi
import pt.isel.ps.g30.tollingsystem.extension.toDate
import javax.inject.Inject


class CreateTransactionWork : Worker() {

    companion object {
        val TAG = SynchronizeUserDataWork::class.simpleName!!
        val KEY_ID = "KEY_ID"
    }

    @Inject
    lateinit var apiService: TollingService

    @Inject
    lateinit var tollingSystemDatabase: TollingSystemDatabase


    fun injectDependencies() {
        (applicationContext as TollingSystemApp).applicationComponent
                .interactors()
        .injectTo(this)
    }


    override fun doWork(): Result {
        injectDependencies()
        val transactionId = this.inputData.getInt(KEY_ID, -1)
        if(transactionId == -1)  return Result.FAILURE

        return runBlocking { //we already have a dedicated thread
            val transaction = tollingSystemDatabase.ActiveTransactionDao().find(transactionId)
            transaction ?: throw Exception("No transaction found with id $transactionId")
            try {

                val openPoints = tollingSystemDatabase.TollingPassageDao().findPassagePoints(transaction.origin!!.id)
                val closePoints = tollingSystemDatabase.TollingPassageDao().findPassagePoints(transaction.destination!!.id)

                val createdTransaction = try{ apiService.createTollingTransaction(transaction.mapForApi(openPoints, closePoints)).await() } catch (e:Exception){return@runBlocking Result.RETRY}
                val vehicle = tollingSystemDatabase.VehicleDao().findById(createdTransaction.vehicle_id)
                val openingPlaza = tollingSystemDatabase.TollingDao().findById(createdTransaction.begin_toll)
                val closingPlaza = tollingSystemDatabase.TollingDao().findById(createdTransaction.end_toll)
                val dbTransaction = createdTransaction.let {
                    TollingTransaction(
                            it.id,
                            it.state,
                            transaction.userId,
                            vehicle,
                            openingPlaza,
                            it.begin_timestamp.toDate(),
                            closingPlaza,
                            it.end_timestamp.toDate()
                    )
                }

                tollingSystemDatabase.TollingTransactionDao().insert(dbTransaction)

                tollingSystemDatabase.TollingPassageDao().delete(transaction.origin!!)

                tollingSystemDatabase.TollingPassageDao().delete(transaction.destination!!)

                tollingSystemDatabase.ActiveTransactionDao().delete(transaction)

                tollingSystemDatabase.NotificationDao().insert(Notification(NotificationType.TransactionNotification,transaction.userId, transaction =dbTransaction))

            }catch (e: Exception){
                Log.e(TAG, "${e.message} - ${e.localizedMessage}")
                return@runBlocking Result.FAILURE
            }
            return@runBlocking Result.SUCCESS
        }
    }
}