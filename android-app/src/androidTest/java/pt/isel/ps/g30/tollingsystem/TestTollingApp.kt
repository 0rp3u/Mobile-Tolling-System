package pt.isel.ps.g30.tollingsystem


import androidx.test.espresso.DaggerBaseLayerComponent
import pt.isel.ps.g30.tollingsystem.injection.component.ApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.component.DaggerApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.module.ApplicationModule
import pt.isel.ps.g30.tollingsystem.injection.module.DatabaseModule
import pt.isel.ps.g30.tollingsystem.injection.module.NetworkModule

class TestTollingApp : TollingSystemApp() {

    override val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .databaseModule(DatabaseModule())
                .applicationModule(ApplicationModule(this))
                //.dataModule(NetworkModule(RESTMockServer.getUrl()))
                .networkModule(NetworkModule("")) //TODO use RESTMOCK
                .build()
    }

}
