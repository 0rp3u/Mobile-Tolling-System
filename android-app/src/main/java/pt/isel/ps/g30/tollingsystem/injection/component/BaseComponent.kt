package pt.isel.ps.g30.tollingsystem.injection.component

import android.content.Context
import dagger.Component
import pt.isel.ps.g30.tollingsystem.injection.module.ApplicationModule
import pt.isel.ps.g30.tollingsystem.injection.module.DatabaseModule
import pt.isel.ps.g30.tollingsystem.injection.module.LocationModule
import pt.isel.ps.g30.tollingsystem.injection.module.NetworkModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    LocationModule::class

])
interface BaseComponent{

    fun inject(context: Context)

}