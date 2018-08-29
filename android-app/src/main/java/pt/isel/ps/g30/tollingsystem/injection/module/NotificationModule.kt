package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor

@Module
class NotificationModule {

    @Provides
    fun provideNotificationInteractor(tollingDatabase: TollingSystemDatabase): NotificationInteractor {
        return NotificationInteractorImpl(tollingDatabase)
    }
}
