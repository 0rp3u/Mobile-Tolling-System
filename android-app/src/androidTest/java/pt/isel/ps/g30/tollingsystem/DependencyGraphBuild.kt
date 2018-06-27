package pt.isel.ps.g30.tollingsystem

import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.injection.module.DatabaseModule
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractorImpl
import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor


fun provideDatabase(app: TollingSystemApp): TollingSystemDatabase {
    return DatabaseModule().providesInMemoryRoomDatabase(app)
}

fun provideAuthInterceptor(): HttpAuthInterceptor {
    return HttpAuthInterceptor()
}

fun provideAuthInteractor(service: TollingService): AuthInteractor {
    return AuthInteractorImpl(service, provideAuthInterceptor())
}


