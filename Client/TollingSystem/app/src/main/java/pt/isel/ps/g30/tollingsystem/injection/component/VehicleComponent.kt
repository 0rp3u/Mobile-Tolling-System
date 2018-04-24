package pt.isel.ps.g30.tollingsystem.injection.component

import dagger.Subcomponent
import pt.isel.ps.g30.tollingsystem.injection.module.VehicleModule
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleActivity
import pt.isel.ps.g30.tollingsystem.view.vehicle.VehicleFragment

@Subcomponent(modules = arrayOf(
        VehicleModule::class
))
interface VehicleComponent {

    fun injectTo(Activity: VehicleActivity)
    fun injectTo(Fragment: VehicleFragment)

}
