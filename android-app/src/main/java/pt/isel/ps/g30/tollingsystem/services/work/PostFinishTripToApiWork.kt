package pt.isel.ps.g30.tollingsystem.services.work

import androidx.work.Data
import androidx.work.*
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.TollingTransaction
import javax.inject.Inject

class PostFinishTripToApiWork : Worker() {

    // Define the parameter keys:
    private val KEY_X_ARG = "X"
    private val KEY_Y_ARG = "Y"
    private val KEY_Z_ARG = "Z"

    // The result key:
    private val KEY_RESULT = "result"

    @Inject
    lateinit var apiService: TollingService


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

        // Fetch the arguments (and specify default values):
        val x = inputData.getLong(KEY_X_ARG, 0)
        val y = inputData.getLong(KEY_Y_ARG, 0)
        val z = inputData.getLong(KEY_Z_ARG, 0)

        val timeToSleep = x  + y + z
        Thread.sleep(timeToSleep)

        runBlocking {
//            val transaction = TollingTransaction()
//            val res = apiService.closeTollingTransaction(transaction)

        }

        //...set the output, and we're done!
        val output = Data.Builder()
                .putInt(KEY_RESULT, timeToSleep.toInt())
                .build()

        outputData = output
        // Indicate success or failure with your return value.
        return Result.SUCCESS
    }
}