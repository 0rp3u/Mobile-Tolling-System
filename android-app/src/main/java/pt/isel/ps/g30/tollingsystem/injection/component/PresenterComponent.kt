package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.services.GeofenceTransitionsJobIntentService
import pt.isel.ps.g30.tollingsystem.injection.module.*
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity
import pt.isel.ps.g30.tollingsystem.view.main.MainActivity
import pt.isel.ps.g30.tollingsystem.view.navigation.NavigationViewFragment
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationFragment
import pt.isel.ps.g30.tollingsystem.view.splash.SplashActivity
import pt.isel.ps.g30.tollingsystem.view.tollingTransaction.TollingTransactionDetails
import pt.isel.ps.g30.tollingsystem.view.tollingTransaction.TollingTransactionsFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivity
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleDetailsDetailFragment
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehiclesFragment


@Subcomponent(modules = [PresentersModule::class, AuthModule::class, TollingModule::class, VehicleModule::class, NotificationModule::class, SynchronizationModule::class])
interface PresenterComponent {

    fun injectTo(Activity: LoginActivity)
    fun injectTo(Activity: SplashActivity)
    fun injectTo(fragment: TollingTransactionsFragment)
    fun injectTo(fragment: VehicleDetailsDetailFragment)
    fun injectTo(fragment: VehiclesFragment)
    fun injectTo(Fragment: NotificationFragment)
    fun injectTo(Fragment: NavigationViewFragment)
    fun injectTo(Activity: MainActivity)
    fun injectTo(Activity: VehicleActivity)
    fun injectTo(Activity: TollingTransactionDetails)
}