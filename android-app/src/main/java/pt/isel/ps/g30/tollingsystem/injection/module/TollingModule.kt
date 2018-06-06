package pt.isel.ps.g30.tollingsystem.injection.module


import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractorImpl
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractorImpl


@Module
class TollingModule {

    @Provides
    fun provideTollingTripInteractor(tollingDatabase: TollingSystemDatabase): TollingTripInteractor =
            TollingTripInteractorImpl(tollingDatabase)

    @Provides
    fun provideTollingPlazaInteractor(tollingDatabase: TollingSystemDatabase): TollingPlazaInteractor =
            TollingPlazaInteractorImpl(tollingDatabase)

}
