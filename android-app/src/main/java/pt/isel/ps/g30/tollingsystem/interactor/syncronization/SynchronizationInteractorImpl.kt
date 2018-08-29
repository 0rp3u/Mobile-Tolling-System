package pt.isel.ps.g30.tollingsystem.interactor.syncronization

import android.content.SharedPreferences
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

        if(workManager.getStatusesByTag(SynchronizeUserDataWork.TAG).value ==null){
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            val request = PeriodicWorkRequestBuilder<SynchronizeUserDataWork>(12, TimeUnit.SECONDS)
                    .setConstraints(constraints)
                    .build()
            workManager.enqueue(request)
        }
    }


    override suspend fun SynchronizeUserData(apiUser: ApiUser){
        val user = tollingSystemDatabase.UserDao().findById(apiUser.id)
                ?: User(apiUser.id, apiUser.name, apiUser.login).also { tollingSystemDatabase.UserDao().insert(it) }

        val apiVehicles = service.getVehicleList().await()
        val dbVehicles = tollingSystemDatabase.VehicleDao().findAll()

        val newVehicles = apiVehicles
                .filterNot { dbVehicle -> dbVehicles.find { dbVehicle.id == it.id } != null}
                .map { Vehicle(it.id, it.licensePlate, it.tare) }

        tollingSystemDatabase.VehicleDao().insert(*newVehicles.toTypedArray())

        synchronizePlazas()



    }


    suspend fun synchronizePlazas(){
        val apiPlazas = service.getAllPlazas().await()

        val dbPlazas = tollingSystemDatabase.TollingDao().findAll()
        val newTolls = apiPlazas
                .filterNot { apiPlaza -> dbPlazas.find { apiPlaza.id == it.id} != null}
                .map { TollingPlaza(it.id,it.name, it.concession, false,it.location_latitude, it.geolocation_longitude) }

        tollingSystemDatabase.TollingDao().insert(*newTolls.toTypedArray())

    }
}
