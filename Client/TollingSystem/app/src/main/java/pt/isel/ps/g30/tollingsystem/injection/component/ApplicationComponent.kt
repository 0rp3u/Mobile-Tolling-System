package pt.isel.ps.g30.tollingsystem.injection.component


import dagger.Component
import pt.isel.ps.g30.tollingsystem.injection.module.ApplicationModule
import pt.isel.ps.g30.tollingsystem.injection.module.DataModule
import pt.isel.ps.g30.tollingsystem.injection.module.VehicleModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        DataModule::class
))
interface ApplicationComponent {

    fun plus(module: VehicleModule): VehicleComponent


}
