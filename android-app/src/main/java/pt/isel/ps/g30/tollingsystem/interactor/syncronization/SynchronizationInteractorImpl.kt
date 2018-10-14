package pt.isel.ps.g30.tollingsystem.interactor.syncronization

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.work.*
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import pt.isel.ps.g30.tollingsystem.extension.dateTimeParsed
import pt.isel.ps.g30.tollingsystem.extension.toDate
import pt.isel.ps.g30.tollingsystem.services.work.SynchronizeUserDataWork
import java.util.*
import java.util.concurrent.TimeUnit


class SynchronizationInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase, private val service: TollingService, private val sharedPreferences: SharedPreferences) : SynchronizationInteractor {


    companion object {
        val LASTUPDATE_KEY = "LASTUPDATE_KEY"
        val LASTUPDATETRANSACTION_KEY = "LASTUPDATETRANSACTION_KEY"
    }

    override suspend fun VerifySynchronization() {

        val workManager : WorkManager = WorkManager.getInstance()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val request = PeriodicWorkRequestBuilder<SynchronizeUserDataWork>(6, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(SynchronizeUserDataWork.TAG)
                .build()

        workManager.enqueueUniquePeriodicWork(
                SynchronizeUserDataWork.TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                request
        )

    }


    override suspend fun SynchronizeUserData(apiUser: ApiUser){
        try {

            val lastUpdate = sharedPreferences.getString(LASTUPDATE_KEY,null)

            val user = tollingSystemDatabase.UserDao().findById(apiUser.id)
                    ?: User(apiUser.id, apiUser.name, apiUser.login).let { tollingSystemDatabase.UserDao().insert(it) }.let { tollingSystemDatabase.UserDao().findById(it.first().toInt()) } ?: throw Exception("vehicle not added")

            val apiVehicles = service.getVehicleList().await()
            val dbVehicles = tollingSystemDatabase.VehicleDao().findAll()

            val toUpdateVehicles = apiVehicles
                    .filter { apiVheicle -> dbVehicles.find { apiVheicle.id == it.id } != null }
                    .map { Vehicle(it.id, it.plate, it.tier, user.id) }

            val newVehicles = apiVehicles
                    .filter { apiVheicle -> dbVehicles.find { apiVheicle.id == it.id } == null }
                    .map { Vehicle(it.id, it.plate, it.tier, user.id) }

            tollingSystemDatabase.VehicleDao().insert(*newVehicles.toTypedArray())
            tollingSystemDatabase.VehicleDao().update(*toUpdateVehicles.toTypedArray())

            synchronizePlazas(lastUpdate)

            synchronizeTransactions(lastUpdate)

            sharedPreferences.edit {
                putString(LASTUPDATE_KEY, Date().dateTimeParsed())
            }

        }catch (e: Exception){
            Log.d("synch", e.localizedMessage)
            throw e
        }

    }

    override suspend fun synchronizeTransactionData() {
        val lastUpdate = sharedPreferences.getString(LASTUPDATETRANSACTION_KEY,null) ?: sharedPreferences.getString(LASTUPDATE_KEY,null)
        synchronizeTransactions(lastUpdate)
        sharedPreferences.edit {
            putString(LASTUPDATETRANSACTION_KEY, Date().dateTimeParsed())
        }
    }

    suspend fun synchronizePlazas(lastUpdate: String?){
        val apiPlazas = service.getAllPlazas(lastUpdate).await()

        val dbPlazas = tollingSystemDatabase.TollingDao().findAll()
        val toUpdate = apiPlazas
                .filter { apiPlaza -> dbPlazas.find { apiPlaza.id == it.id} != null}
                .map { TollingPlaza(
                        it.id,
                        it.name,
                        it.concession,
                        false,
                        it.geolocation_latitude,
                        it.geolocation_longitude,
                        it.open_toll) }

        val toInsert = apiPlazas
                .filter { apiPlaza -> dbPlazas.find { apiPlaza.id == it.id} == null}
                .map { TollingPlaza(
                        it.id,
                        it.name,
                        it.concession,
                        dbPlazas.find { dbplaza -> it.id == dbplaza.id }?.active ?: false,
                        it.geolocation_latitude,
                        it.geolocation_longitude,
                        it.open_toll)
                }

        tollingSystemDatabase.TollingDao().insert(*toInsert.toTypedArray())

        tollingSystemDatabase.TollingDao().update(*toUpdate.toTypedArray())
    }

    suspend fun synchronizeTransactions(lastUpdate:String?){
        val transactions = service.getTransactionList(lastUpdate).await()

        val dbTransactions = tollingSystemDatabase.TollingTransactionDao().findAll()

        val toUpdate = transactions.filter { apiTransaction -> dbTransactions.find { apiTransaction.id == it.id} != null}
                .map { TollingTransaction(
                        it.id,
                        it.state,
                        it.user_id,
                        tollingSystemDatabase.VehicleDao().findById(it.vehicle_id),
                        tollingSystemDatabase.TollingDao().findById(it.begin_toll),
                        it.begin_timestamp.toDate(),
                        tollingSystemDatabase.TollingDao().findById(it.end_toll),
                        it.end_timestamp.toDate(),
                        it.billing
                )}
        val toInsert = transactions.filter { apiTransaction -> dbTransactions.find { apiTransaction.id == it.id} == null}
                .map { TollingTransaction(
                        it.id,
                        it.state,
                        it.user_id,
                        tollingSystemDatabase.VehicleDao().findById(it.vehicle_id),
                        tollingSystemDatabase.TollingDao().findById(it.begin_toll),
                        it.begin_timestamp.toDate(),
                        tollingSystemDatabase.TollingDao().findById(it.end_toll),
                        it.end_timestamp.toDate(),
                        it.billing
                )}

        toUpdate.filter { it.STATE == TransactionState.CLOSED }
                .forEach {
                    tollingSystemDatabase.NotificationDao()
                            .insert(Notification(
                                    NotificationType.TransactionPaidNotification,
                                    it.userId,
                                    it.vehicle,
                                    it)
                            )
                }




        tollingSystemDatabase.TollingTransactionDao().insert(*toInsert.toTypedArray())

        tollingSystemDatabase.TollingTransactionDao().updateTransaction(*toUpdate.toTypedArray())
    }
}
