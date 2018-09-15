package pt.isel.ps.g30.tollingsystem.injection.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.interactor.user.UserInteractor
import pt.isel.ps.g30.tollingsystem.interactor.user.UserInteractorImpl

@Module
class UserModule {

    @Provides
    fun provideUserInteractor(tollingSystemDatabase: TollingSystemDatabase): UserInteractor
            =UserInteractorImpl(tollingSystemDatabase)
    }
