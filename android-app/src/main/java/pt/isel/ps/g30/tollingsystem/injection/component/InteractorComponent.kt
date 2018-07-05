package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.services.GeofenceTransitionsJobIntentService
import pt.isel.ps.g30.tollingsystem.injection.module.*
import pt.isel.ps.g30.tollingsystem.services.work.PostFinishTripToApiWork
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationViewFragment
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationFragment
import pt.isel.ps.g30.tollingsystem.view.splash.SplashActivity
import pt.isel.ps.g30.tollingsystem.view.tollingtrip.TollingTripsFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleDetailsDetailFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehiclesFragment


@Subcomponent(modules = [AuthModule::class, TollingModule::class, VehicleModule::class, NotificationModule::class])
interface InteractorComponent {

    fun injectTo(service: GeofenceTransitionsJobIntentService)
    fun injectTo(worker: PostFinishTripToApiWork)
}