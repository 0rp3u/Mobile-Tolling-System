package pt.isel.ps.g30.tollingsystem



import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import dagger.Component
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.injection.component.ApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.component.BaseComponent
import pt.isel.ps.g30.tollingsystem.injection.component.DaggerApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.module.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Singleton
    @Component(modules = [
        ApplicationModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        LocationModule::class

    ])
    interface TestApplicationComponent: BaseComponent {
        fun injectTo(databaseTest: ExampleInstrumentedTest)
    }

    @Inject
    lateinit var mockDatabase : TollingSystemDatabase

    @Before
    fun inject(){
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as TollingSystemApp
        (app.applicationComponent as TestApplicationComponent).injectTo(this)

    }


    @Test
    public fun testGetDatabase(){
        assert(mockDatabase.VehicleDao().findAll().isNotEmpty())

    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("pt.isel.ps.g30.tollingsystem", appContext.packageName)
    }
}
