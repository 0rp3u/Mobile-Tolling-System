package pt.isel.ps.g30.tollingsystem.injection.component


import dagger.Component
import pt.isel.ps.g30.tollingsystem.injection.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    LocationModule::class

])

interface ApplicationComponent : BaseComponent{

    fun plus(module: PresentersModule): PresenterComponent
    fun interactors(): InteractorComponent

}
