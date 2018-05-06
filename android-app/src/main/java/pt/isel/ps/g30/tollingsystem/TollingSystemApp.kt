package pt.isel.ps.g30.tollingsystem

import android.app.Application
import pt.isel.ps.g30.tollingsystem.injection.component.DaggerApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.component.ApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.module.ApplicationModule
import pt.isel.ps.g30.tollingsystem.injection.module.DataModule

open class TollingSystemApp : Application() {

    open val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .dataModule(DataModule("http://localhost:8080"))
                .build()
    }

}