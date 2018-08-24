package pt.isel.ps.g30.tollingsystem.services.work

import androidx.work.Data
import androidx.work.*
import kotlinx.coroutines.experimental.runBlocking
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase_Impl
import javax.inject.Inject

class verifyTollingPassageWork : Worker() {

    // Define the parameter keys:
    private val KEY_PLAZA_ID = "KEY_PLAZA_ID"

    // The result key:
    private val KEY_RESULT = "result"

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

        // Fetch the arguments (and specify default values):
        val plaza_id = inputData.getLong(KEY_PLAZA_ID, -1)


        runBlocking {
//            val transaction = TollingTransaction()
//            val res = apiService.verifyTollingTransaction()

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