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
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor
import javax.inject.Inject

class SynchronizeUserDataWork : Worker() {

    companion object {
        val TAG = SynchronizeUserDataWork::class.simpleName!!
    }

    @Inject
    lateinit var apiService: TollingService

    @Inject
    lateinit var synchronizationInteractor: SynchronizationInteractor


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

        return runBlocking {
            try{
                val auth = apiService.authenticate().await()

                if (auth.isSuccessful && auth.body() != null){
                    synchronizationInteractor.SynchronizeUserData(auth.body()!!)

                    return@runBlocking Result.SUCCESS
                }else
                    return@runBlocking Result.FAILURE

            }catch (e:Exception){
                return@runBlocking Result.RETRY
            }

        }

    }
}