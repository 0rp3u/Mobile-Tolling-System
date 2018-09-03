package pt.isel.ps.g30.tollingsystem.services.work

import androidx.work.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.Point as ApiPoint
import pt.isel.ps.g30.tollingsystem.data.api.model.TollPassageInfo
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
        injectDependencies()

        runBlocking {
           tollingSystemDatabase.TollingPassageDao().findAllPending().forEach {
               val passagePoints = tollingSystemDatabase.TollingPassageDao()
                       .findPassagePoints(it.id)
                       .map {point-> point.mapForApi() }

               val passageInfo = TollPassageInfo(it.plaza.id, passagePoints)

               val passed = apiService.verifyTollPassage(passageInfo).await()

               if (passed) {
                   val currentTransaction = tollingTransactionInteractor.getCurrentTransactionTransaction().await()

                   if (currentTransaction.origin != null) {
                       tollingTransactionInteractor.finishTransaction(it)

                   } else {
                       tollingTransactionInteractor.startTollingTransaction(it).await()
                   }

               }
           }

        }

//        //...set the output, and we're done!
//        val output = Data.Builder()
//                .putInt(KEY_RESULT, timeToSleep.toInt())
//                .build()
//
//        outputData = output
        // Indicate success or failure with your return value.
        return Result.SUCCESS
    }
}