package pt.isel.ps.g30.tollingsystem.services.work

import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.TransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.data.database.model.NotificationType
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.data.mapForApi
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
        //.injectTo(this)
    }


    override fun doWork(): Result {
        injectDependencies()
        val transactionId = this.inputData.getInt(KEY_ID, -1)
        if(transactionId == -1)  return Result.FAILURE

        runBlocking { //we already have a dedicated thread
            val transaction = tollingSystemDatabase.ActiveTransactionDao().find(transactionId)
            transaction ?: throw Exception("No transaction found with id $transactionId")
            try {

                val openPoints = tollingSystemDatabase.TollingPassageDao().findPassagePoints(transaction.origin!!.id)
                val closePoints = tollingSystemDatabase.TollingPassageDao().findPassagePoints(transaction.destination!!.id)

                val createdTransaction = apiService.createTollingTransaction(transaction.mapForApi(openPoints, closePoints)).await()
                val vehicle = tollingSystemDatabase.VehicleDao().findById(createdTransaction.vehicleId)
                val openingPlaza = tollingSystemDatabase.TollingDao().findById(createdTransaction.openPlaza)
                val closingPlaza = tollingSystemDatabase.TollingDao().findById(createdTransaction.closePlaza!!)
                val dbTransaction = createdTransaction.let {
                    TollingTransaction(
                            it.id,
                            transaction.userId,
                            vehicle,
                            openingPlaza,
                            it.openTimestamp,
                            closingPlaza,
                            it.closeTimestamp
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
        }

        return Result.SUCCESS
    }
}