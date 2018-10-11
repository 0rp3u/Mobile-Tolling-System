package pt.isel.ps.g30.tollingsystem.services.work

import androidx.work.*
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.Point as ApiPoint
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.mapForApi
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractor

import javax.inject.Inject

class VerifyTollingPassageWork : Worker() {

    companion object {
        val TAG = VerifyTollingPassageWork::class.java.simpleName
    }

    @Inject
    lateinit var apiService: TollingService

    @Inject
    lateinit var tollingSystemDatabase: TollingSystemDatabase

    @Inject
    lateinit var tollingTransactionInteractor: TollingTransactionInteractor


    fun injectDependencies() {
        (applicationContext as TollingSystemApp).applicationComponent
                .interactors()
                .injectTo(this)
    }
    /**
     * This will be called whenever work manager run the work.
     */
    override fun doWork(): Result {
        return try {
            injectDependencies()

            runBlocking {
                tollingSystemDatabase.TollingPassageDao().findAllPending().forEach {
                    val passagePoints = tollingSystemDatabase.TollingPassageDao()
                            .findPassagePoints(it.id)
                            .map { point -> point.mapForApi() }


                    val confidence = apiService.verifyTollPassage(it.plaza.id, passagePoints).await()

                    if (confidence > 0.8) {
                        val currentTransaction = tollingTransactionInteractor.getCurrentTransactionTransaction().await()

                        if (currentTransaction.origin != null) {
                            tollingTransactionInteractor.finishTransaction(it)

                        } else {
                            tollingTransactionInteractor.startTollingTransaction(it).await()
                        }
                    }
                }
                return@runBlocking Result.SUCCESS
            }
        }catch (e: Exception){
            return Result.RETRY
        }

    }
}