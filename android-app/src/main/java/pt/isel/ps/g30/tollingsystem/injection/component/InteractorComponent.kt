package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.services.GeofenceTransitionsJobIntentService
import pt.isel.ps.g30.tollingsystem.injection.module.*
import pt.isel.ps.g30.tollingsystem.services.work.GetNearTollPazasWork
import pt.isel.ps.g30.tollingsystem.services.work.GetTollPazasWork
import pt.isel.ps.g30.tollingsystem.services.work.verifyTollingPassageWork


@Subcomponent(modules = [AuthModule::class, TollingModule::class, VehicleModule::class, NotificationModule::class])
interface InteractorComponent {

    fun injectTo(service: GeofenceTransitionsJobIntentService)
    fun injectTo(worker: verifyTollingPassageWork)
    fun injectTo(worker: GetTollPazasWork)
    fun injectTo(worker: GetNearTollPazasWork)
}