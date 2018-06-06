package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractorImpl

@Module
class VehicleModule {

    @Provides
    fun provideVehicleInteractor(tollingDatabase: TollingSystemDatabase): VehicleInteractor =
        VehicleInteractorImpl(tollingDatabase)
}
