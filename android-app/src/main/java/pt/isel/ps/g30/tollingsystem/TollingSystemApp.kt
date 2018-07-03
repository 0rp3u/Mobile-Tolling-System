package pt.isel.ps.g30.tollingsystem

import android.app.Application
import pt.isel.ps.g30.tollingsystem.injection.component.DaggerApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.component.ApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.module.ApplicationModule
import pt.isel.ps.g30.tollingsystem.injection.module.NetworkModule
import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary
import pt.isel.ps.g30.tollingsystem.injection.component.BaseComponent


open class TollingSystemApp : Application() {

    companion object {
        lateinit var instance: TollingSystemApp
            private set
    }

    open val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule("http://10.10.3.213:8081"))
                .build()
    }


    override fun onCreate() {
        super.onCreate()
        setupLeakCanary()
        instance = this
    }

    private fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        enabledStrictMode()
        LeakCanary.install(this)
    }

    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .permitDiskReads() //for dagger dependency injection
                .penaltyLog() //
                .penaltyDeath() //
                .build())
    }
}