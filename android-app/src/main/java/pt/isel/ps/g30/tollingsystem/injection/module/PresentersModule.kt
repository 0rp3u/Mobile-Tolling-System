package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.geofencing.GeofencingInteractor
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingTransaction.TollingTransactionInteractor
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenter
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.main.MainPresenter
import pt.isel.ps.g30.tollingsystem.presenter.main.MainPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.navigation.NavigationFragPresenter
import pt.isel.ps.g30.tollingsystem.presenter.navigation.NavigationFragPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenter
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.splash.SplashPresenter
import pt.isel.ps.g30.tollingsystem.presenter.splash.SplashPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction.TollingTransactionDetailsPresenter
import pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction.TollingTransactionDetailsPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction.TollingTransactionFragPresenter
import pt.isel.ps.g30.tollingsystem.presenter.tollingTransaction.TollingTransactionFragPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.vehicle.*

@Module
class PresentersModule {
    
    @Provides
    fun provideLoginPresenter(interactor: AuthInteractor): LoginPresenter
            = LoginPresenterImpl(interactor)

    @Provides
    fun provideSplashPresenter(interactor: AuthInteractor): SplashPresenter
            = SplashPresenterImpl(interactor)

    @Provides
    fun provideTollingTransactionFragPresenter(authInteractor: AuthInteractor, interactor: TollingTransactionInteractor): TollingTransactionFragPresenter
            = TollingTransactionFragPresenterImpl(authInteractor, interactor)

    @Provides
    fun provideNavigationPresenter(authInteractor: AuthInteractor, tollingTransactionInteractor: TollingTransactionInteractor, tollingPlazaInteractor: TollingPlazaInteractor, vehicleInteractor: VehicleInteractor, geofencingInteractor: GeofencingInteractor): NavigationFragPresenter
            = NavigationFragPresenterImpl(authInteractor,tollingTransactionInteractor, tollingPlazaInteractor, vehicleInteractor, geofencingInteractor)

    @Provides
    fun provideVehicleFragPresenter(authInteractor: AuthInteractor, interactor: VehicleInteractor): VehiclesFragPresenter
            = VehiclesFragPresenterImpl(authInteractor, interactor)

    @Provides
    fun provideVehiclePresenter(authInteractor: AuthInteractor, interactor: VehicleInteractor, transactionInteractor: TollingTransactionInteractor): VehicleDetailsPresenter
            = VehicleDetailsPresenterImpl(authInteractor, interactor, transactionInteractor)

    @Provides
    fun provideNotificationPresenter(authInteractor: AuthInteractor, interactor: NotificationInteractor, synchronizationInteractor: SynchronizationInteractor): NotificationPresenter
            = NotificationPresenterImpl(authInteractor, interactor, synchronizationInteractor)

    @Provides
    fun provideMainPresenter(authInteractor: AuthInteractor, interactor: NotificationInteractor): MainPresenter
            = MainPresenterImpl(authInteractor, interactor)

    @Provides
    fun providetollingTransactionDetailsPresenter(authInteractor: AuthInteractor, interactor: TollingTransactionInteractor): TollingTransactionDetailsPresenter
            = TollingTransactionDetailsPresenterImpl(authInteractor, interactor)

    @Provides
    fun provideVehicleActivityPresenter(authInteractor: AuthInteractor): VehicleActivityPresenter
            = VehicleActivityPresenterImpl(authInteractor)

}