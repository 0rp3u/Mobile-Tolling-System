package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.services.GeofenceTransitionsJobIntentService
import pt.isel.ps.g30.tollingsystem.injection.module.*
import pt.isel.ps.g30.tollingsystem.services.work.*


@Subcomponent(modules = [AuthModule::class, TollingModule::class, VehicleModule::class, NotificationModule::class, SynchronizationModule::class,UserModule::class])
interface InteractorComponent {

    fun injectTo(service: GeofenceTransitionsJobIntentService)
    fun injectTo(worker: VerifyTollingPassageWork)
    fun injectTo(worker: SynchronizeUserDataWork)
    fun injectTo(worker: GetNearTollPazasWork)
    fun injectTo(worker: CreateTransactionWork)
    fun injectTo(worker: ConfirmTransactionWork)
}