package pt.isel.ps.g30.tollingsystem.services.work

import androidx.work.Data
import androidx.work.Worker
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.extension.dateTimeParsed
import pt.isel.ps.g30.tollingsystem.data.api.model.TollingTransaction as ApiTransaction
import javax.inject.Inject


class ConfirmTransactionWork : Worker() {

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
            val transaction = tollingSystemDatabase.TollingTransactionDao().findById(transactionId)
            transaction ?: throw Exception("no transaction found with id $transactionId")
            try {

                apiService.confirmTollingTransaction(
                        transactionId,
                        transaction.let { ApiTransaction(it.id,it.vehicle.id, it.origin.id, it.originTimestamp.dateTimeParsed(), it.destination.id, it.destTimestamp.dateTimeParsed()) }
                ).await()

                tollingSystemDatabase.TollingTransactionDao().updateTransaction(transaction)
            }catch (e: Exception){
                Result.RETRY
            }

            return@runBlocking Result.SUCCESS
        }
    }
}