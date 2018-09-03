package pt.isel.ps.g30.tollingsystem.injection.module


import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractorImpl
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractorImpl
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractor


@Module
class TollingModule {

    @Provides
    fun provideTollingTransactionInteractor(tollingDatabase: TollingSystemDatabase, notificationInteractor: NotificationInteractor): TollingTransactionInteractor =
            TollingTransactionInteractorImpl(tollingDatabase, notificationInteractor)

    @Provides
    fun provideTollingPlazaInteractor(tollingDatabase: TollingSystemDatabase, tollingService: TollingService): TollingPlazaInteractor =
            TollingPlazaInteractorImpl(tollingDatabase,tollingService)

}
