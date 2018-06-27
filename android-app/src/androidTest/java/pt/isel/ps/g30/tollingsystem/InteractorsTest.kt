package pt.isel.ps.g30.tollingsystem

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pt.isel.ps.g30.tollingsystem.data.api.TollingService

@RunWith(AndroidJUnit4::class)
class InteractorsTest {


    lateinit var app: TollingSystemApp


    @Before
    fun dependencies() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        app = instrumentation.targetContext.applicationContext as TollingSystemApp
    }


    @Test
    fun authTest() {
//      val authInteractor = provideAuthInteractor(service)
//
//        runBlocking {
//            assert(authInteractor.authenticate("","") .await())
//
//
//        }
//
    }

}