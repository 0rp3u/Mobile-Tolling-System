package pt.isel.ps.g30.tollingsystem.data.Repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.extension.toFinishedTransaction
import pt.isel.ps.g30.tollingsystem.utils.NetworkUtils
import java.util.*

class TollingTransactionRepository(private val tollingSystemDatabase: TollingSystemDatabase, private val apiService: TollingService, private val networkUtils: NetworkUtils ){


    fun getVehicleTransactionList(vehicleId: Int): Deferred<List<pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTransactionDao().findByVehicle(vehicleId) }

    }

    fun getTollingTransactionList() : Deferred<List<pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction>>{
        return async { tollingSystemDatabase.TollingTransactionDao().findAll() }
    }

    fun getTollingTransaction(id: Int): Deferred<pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction> {
        return async { tollingSystemDatabase.TollingTransactionDao().findById(id) ?: throw Exception("Could not find transaction")}
    }

    fun startTollingTransaction(origin: TollingPlaza): Deferred<CurrentTransaction> {
        return async {

            val tollingTransaction = tollingSystemDatabase.ActiveTransactionDao().find()
            tollingTransaction.vehicle ?: throw Exception("Could not start transaction, no active vehicle found")


            tollingTransaction.origin = origin
            tollingTransaction.destTimestamp =  Date()

            if(tollingSystemDatabase.ActiveTransactionDao().update(tollingTransaction) ==  0)  throw Exception("Could not start transaction")

            if(networkUtils.isConnected()) launch{apiService.initiateTollingTransaction(TollingTransaction(tollingTransaction.id, tollingTransaction.originTimestamp!!, tollingTransaction.vehicle?.id!!, tollingTransaction.origin?.id!!)).await()}

            return@async tollingTransaction
        }
    }

    suspend fun finishTollingTransaction(dest: TollingPlaza): Deferred<CurrentTransaction> {
        return async {
            val activeTransaction = tollingSystemDatabase.ActiveTransactionDao().find()

            if(activeTransaction.origin == null || activeTransaction.vehicle== null) throw Exception("Could not finish transaction, no active transaction found")


            activeTransaction.destination = dest
            activeTransaction.destTimestamp =  Calendar.getInstance().time

            val insertedId = tollingSystemDatabase.TollingTransactionDao().insert(activeTransaction.toFinishedTransaction())[0]
            val insertedTollingTransaction = tollingSystemDatabase.TollingTransactionDao().findById(insertedId.toInt())
                    ?: throw Exception("Could not finish transaction")

            activeTransaction.origin = null

            if(tollingSystemDatabase.ActiveTransactionDao().update(activeTransaction) ==  0)  throw Exception("Could not finish transaction")

            if(networkUtils.isConnected()) launch{apiService.closeTollingTransaction(TollingTransaction(insertedTollingTransaction.id, insertedTollingTransaction.originTimestamp, insertedTollingTransaction.vehicle.id, insertedTollingTransaction.origin.id, insertedTollingTransaction.destination.id)).await()}

            return@async activeTransaction
        }
    }

    fun getActiveTollingTransactionLiveData(): Deferred<LiveData<CurrentTransaction>> {
        return async { tollingSystemDatabase.ActiveTransactionDao().findLiveData() }
    }


    fun getActiveTollingTransaction(): Deferred<CurrentTransaction> {
        return async { tollingSystemDatabase.ActiveTransactionDao().find() }
    }

    fun cancelActiveTransaction(Transaction: CurrentTransaction): Deferred<Int>{
        return async {
            if(networkUtils.isConnected()) {
                apiService.cancelTollingTransaction(TollingTransaction(Transaction.id, Transaction.originTimestamp!!, Transaction.vehicle?.id!!, Transaction.origin?.id!!, Transaction.destination?.id)).await()

                tollingSystemDatabase.ActiveTransactionDao().update(Transaction.apply { origin = null; originTimestamp = null })
            }else
                throw Exception("Not connected to the internet, you need to be connected")
        }
    }
}