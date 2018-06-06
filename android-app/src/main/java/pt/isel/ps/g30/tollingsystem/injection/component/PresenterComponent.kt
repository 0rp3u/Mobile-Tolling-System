package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.geofencing.GeofenceTransitionsJobIntentService
import pt.isel.ps.g30.tollingsystem.injection.module.*
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationViewFragment
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationFragment
import pt.isel.ps.g30.tollingsystem.view.splash.SplashActivity
import pt.isel.ps.g30.tollingsystem.view.tollingTripInfo.TollingTripInfoActivity
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragmentFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivity
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehiclesFragmentFragment
import javax.inject.Scope


@Subcomponent(modules = [PresentersModule::class, AuthModule::class, TollingModule::class, VehicleModule::class, NotificationModule::class])
interface PresenterComponent {

    fun injectTo(Activity: LoginActivity)
    fun injectTo(Activity: SplashActivity)
    //fun injectTo(Activity: TripHistoryActivity)
    fun injectTo(fragment: TollingTripsFragmentFragment)
    fun injectTo(Activity: VehicleActivity)
    fun injectTo(Fragment: VehiclesFragmentFragment)
    fun injectTo(Fragment: NotificationFragment)
    fun injectTo(Activity: TollingTripInfoActivity)
    fun injectTo(Fragment: NavigationViewFragment)
    fun injectTo(service: GeofenceTransitionsJobIntentService ) //TODO separate this from here, since service does not need a presenter
}