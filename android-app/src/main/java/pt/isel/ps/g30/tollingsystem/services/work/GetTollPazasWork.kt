package pt.isel.ps.g30.tollingsystem.services.work

import androidx.work.Data
import androidx.work.*
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase_Impl
import pt.isel.ps.g30.tollingsystem.data.api.model.TollingPlaza as apiPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import javax.inject.Inject

class GetTollPazasWork : Worker() {

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
    override fun doWork(): Result {
        injectDependencies()

        runBlocking {
            val plazas = apiService.getAllPlazas().await()
            tollingSystemDatabase.TollingDao().insert(
                    *plazas.map {
                        TollingPlaza(it.name,
                                it.concession,
                                false,
                                it.latLong.lat,
                                it.latLong.long,
                                it.openToll,
                                it.id)
                    }.toTypedArray())

        }
        return Result.SUCCESS
    }
}