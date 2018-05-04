package pt.isel.ps.g30.tollingsystem.injection.component


import dagger.Component
import pt.isel.ps.g30.tollingsystem.injection.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        DataModule::class
))
interface ApplicationComponent {

    fun plus(module: VehicleModule): VehicleComponent
    fun plus(module: NotificationModule): NotificationComponent
    fun plus(module: AuthModule): LoginComponent


}
