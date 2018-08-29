package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractorImpl
import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor

@Module
class AuthModule {

    @Provides
    fun provideAuthInteractor(tollingService: TollingService, authInterceptor: HttpAuthInterceptor, synchronizationInteractor: SynchronizationInteractor): AuthInteractor
            =AuthInteractorImpl(tollingService,authInterceptor,synchronizationInteractor)
    }
