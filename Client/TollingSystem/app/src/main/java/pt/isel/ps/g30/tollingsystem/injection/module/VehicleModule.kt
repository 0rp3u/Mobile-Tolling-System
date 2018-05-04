package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.api.TollingService
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractorImpl

@Module
class VehicleModule {

    @Provides
    fun provideVehicleInteractor(tollingService: TollingService): VehicleInteractor {
        return VehicleInteractorImpl(tollingService)
    }

}
