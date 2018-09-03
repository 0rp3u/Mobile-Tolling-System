package pt.isel.ps.g30.tollingsystem.services.work

import androidx.work.Data
import androidx.work.Worker
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction
import javax.inject.Inject


class CancelTransactionWork : Worker() {

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
            val transaction = tollingSystemDatabase.TollingDao().findById(transactionId)
            try {

                apiService.cancelTollingTransaction(transactionId).await()
                tollingSystemDatabase.TollingDao().delete(transaction)
            }catch (e: Exception){
                Result.RETRY
            }


        }

        return Result.SUCCESS
    }
}