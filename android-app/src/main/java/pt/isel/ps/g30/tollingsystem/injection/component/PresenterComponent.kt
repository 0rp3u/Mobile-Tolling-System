package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.services.GeofenceTransitionsJobIntentService
import pt.isel.ps.g30.tollingsystem.injection.module.*
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationViewFragment
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationFragment
import pt.isel.ps.g30.tollingsystem.view.splash.SplashActivity
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleDetailsDetailFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehiclesFragment


@Subcomponent(modules = [PresentersModule::class, AuthModule::class, TollingModule::class, VehicleModule::class, NotificationModule::class])
interface PresenterComponent {

    fun injectTo(Activity: LoginActivity)
    fun injectTo(Activity: SplashActivity)
    fun injectTo(fragment: TollingTripsFragment)
    fun injectTo(fragment: VehicleDetailsDetailFragment)
    fun injectTo(fragment: VehiclesFragment)
    fun injectTo(Fragment: NotificationFragment)
    fun injectTo(Fragment: NavigationViewFragment)
    fun injectTo(service: GeofenceTransitionsJobIntentService ) //TODO separate this from here, since service does not need a presenter
}