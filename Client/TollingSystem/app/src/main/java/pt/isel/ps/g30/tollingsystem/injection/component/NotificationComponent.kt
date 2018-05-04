package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.injection.module.NotificationModule
import pt.isel.ps.g30.tollingsystem.view.notifications.NotificationFragment


@Subcomponent(modules = arrayOf(
        NotificationModule::class
))
interface NotificationComponent {

    fun injectTo(Fragment: NotificationFragment)

}
