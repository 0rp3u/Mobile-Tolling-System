package pt.isel.ps.g30.tollingsystem.injection.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractorImpl

@Module
class SynchronizationModule {

    @Provides
    fun provideSynchronizationInteractor(tollingSystemDatabase: TollingSystemDatabase, service: TollingService, sharedPreferences: SharedPreferences): SynchronizationInteractor {
        return SynchronizationInteractorImpl(tollingSystemDatabase, service, sharedPreferences)
    }
}
