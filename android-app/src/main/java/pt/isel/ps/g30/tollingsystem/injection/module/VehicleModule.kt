package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.api.TollingService
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractorImpl
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclePresenter
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.VehiclePresenterImpl

@Module
class VehicleModule {

    @Provides
    fun provideVehicleInteractor(tollingService: TollingService): VehicleInteractor =
        VehicleInteractorImpl(tollingService)


    @Provides
    fun provideVehiclePresenter(interactor: VehicleInteractor): VehiclePresenter =
        VehiclePresenterImpl(interactor)



}
