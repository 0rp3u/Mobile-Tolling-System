package pt.isel.ps.g30.tollingsystem.services.work

import android.annotation.SuppressLint
import androidx.work.Data
import androidx.work.*
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.LatLong
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase_Impl
import pt.isel.ps.g30.tollingsystem.data.api.model.TollingPlaza as apiPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import javax.inject.Inject

class GetNearTollPazasWork : Worker() {

    @Inject
    lateinit var apiService: TollingService

    @Inject
    lateinit var tollingSystemDatabase: TollingSystemDatabase


    fun injectDependencies() {
        (applicationContext as TollingSystemApp).applicationComponent
                .interactors()
                .injectTo(this)
    }
    /**
     * This will be called whenever work manager run the work.
     */
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        injectDependencies()
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(TollingSystemApp.instance)

        runBlocking {
        val location = mFusedLocationClient.lastLocation.result

            val plazas = apiService.getNearPlazas(LatLong(location.latitude, location.longitude)).await()
            val activeInDB = tollingSystemDatabase.TollingDao().findActive()
            val notActiveAnymore: List<TollingPlaza> = activeInDB.filterNot { databasePlaza -> plazas.any { it.id == databasePlaza.id}}
            tollingSystemDatabase.TollingDao().update(
                    *notActiveAnymore.map { it.active = false; it }.toTypedArray()
            )

            val nowActivePlazas = plazas.filterNot { nearTolls -> activeInDB.any { it.id == nearTolls.id } }.map { tollingSystemDatabase.TollingDao().findById(it.id).also { it.active = true } }

            tollingSystemDatabase.TollingDao().update(*nowActivePlazas.toTypedArray())
        }

        return Result.SUCCESS
    }
}