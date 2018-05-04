package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.api.TollingService
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractorImpl

@Module
class NotificationModule {

    @Provides
    fun provideVNotificationInteractor(tollingService: TollingService): NotificationInteractor {
        return NotificationInteractorImpl(tollingService)
    }

}
