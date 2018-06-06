package pt.isel.ps.g30.tollingsystem


import pt.isel.ps.g30.tollingsystem.injection.component.ApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.component.DaggerApplicationComponent
import pt.isel.ps.g30.tollingsystem.injection.module.ApplicationModule
import pt.isel.ps.g30.tollingsystem.injection.module.NetworkModule

class TestTollingApp : TollingSystemApp() {

    override val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                //.dataModule(NetworkModule(RESTMockServer.getUrl()))
                .dataModule(NetworkModule("")) //TODO use RESTMOCK
                .build()
    }

}
