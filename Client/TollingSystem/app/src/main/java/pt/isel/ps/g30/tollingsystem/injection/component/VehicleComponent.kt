package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.injection.module.VehicleModule
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivity

@Subcomponent(modules = arrayOf(
        VehicleModule::class
))
interface VehicleComponent {

    fun injectTo(Activity: VehicleActivity)

}
