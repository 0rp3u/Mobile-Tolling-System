package pt.isel.ps.g30.tollingsystem.interactor.syncronization

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import pt.isel.ps.g30.tollingsystem.services.work.SynchronizeUserDataWork
import java.util.concurrent.TimeUnit


class SynchronizationInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase, private val service: TollingService, private val sharedPreferences: SharedPreferences) : SynchronizationInteractor {


    override suspend fun VerifySynchronization() {

        val workManager : WorkManager = WorkManager.getInstance()
        val workInfo = workManager.getStatusesByTag(SynchronizeUserDataWork.TAG)
        val observer = Observer<MutableList<WorkStatus>>{
            if (it == null || it.isEmpty()) {
                val constraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                val request = PeriodicWorkRequestBuilder<SynchronizeUserDataWork>(12, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .addTag(SynchronizeUserDataWork.TAG)
                        .build()
                workManager.enqueue(request)
            }
        }

        workInfo.observeForever(observer)


    }


    override suspend fun SynchronizeUserData(apiUser: ApiUser){
        try {

            val user = tollingSystemDatabase.UserDao().findById(apiUser.id)
                    ?: User(apiUser.id, apiUser.name, apiUser.login).also { tollingSystemDatabase.UserDao().insert(it) }

            val apiVehicles = service.getVehicleList().await()
            val dbVehicles = tollingSystemDatabase.VehicleDao().findAll()

            val newVehicles = apiVehicles
                    .filterNot { dbVehicle -> dbVehicles.find { dbVehicle.id == it.id } != null }
                    .map { Vehicle(it.id, it.plate, it.tier) }

            tollingSystemDatabase.VehicleDao().insert(*newVehicles.toTypedArray())

            synchronizePlazas()

        }catch (e: Exception){
            Log.d("synch", e.localizedMessage)

        }

    }


    suspend fun synchronizePlazas(){
        try {
        val apiPlazas = service.getAllPlazas().await()

        val dbPlazas = tollingSystemDatabase.TollingDao().findAll()
        val newTolls = apiPlazas
                .filterNot { apiPlaza -> dbPlazas.find { apiPlaza.id == it.id} != null}
                .map { TollingPlaza(it.id,it.name, it.concession, false,it.geolocation_latitude, it.geolocation_longitude) }

        tollingSystemDatabase.TollingDao().insert(*newTolls.toTypedArray())
        }catch (e: Exception){
            Log.d("synch", e.localizedMessage)

        }

    }
}
