package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.geofencing.GeofencingInteractor
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
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
    fun provideTollingTransactionFragPresenter(interactor: TollingTransactionInteractor): TollingTransactionFragPresenter
            = TollingTransactionFragPresenterImpl(interactor)

    @Provides
    fun provideNavigationPresenter(tollingTransactionInteractor: TollingTransactionInteractor, tollingPlazaInteractor: TollingPlazaInteractor, vehicleInteractor: VehicleInteractor, geofencingInteractor: GeofencingInteractor): NavigationFragPresenter
            = NavigationFragPresenterImpl(tollingTransactionInteractor, tollingPlazaInteractor, vehicleInteractor, geofencingInteractor)

    @Provides
    fun provideVehicleFragPresenter(interactor: VehicleInteractor): VehiclesFragPresenter
            = VehiclesFragPresenterImpl(interactor)

    @Provides
    fun provideVehiclePresenter(interactor: VehicleInteractor, transactionInteractor: TollingTransactionInteractor): VehicleDetailsPresenter
            = VehicleDetailsPresenterImpl(interactor, transactionInteractor)

    @Provides
    fun provideNotificationPresenter(interactor: NotificationInteractor): NotificationPresenter
            = NotificationPresenterImpl(interactor)

    @Provides
    fun provideMainPresenter(interactor: NotificationInteractor): MainPresenter
            = MainPresenterImpl(interactor)

    @Provides
    fun providetollingTransactionDetailsPresenter(interactor: TollingTransactionInteractor): TollingTransactionDetailsPresenter
            = TollingTransactionDetailsPresenterImpl(interactor)

    @Provides
    fun provideVehicleActivityPresenter(): VehicleActivityPresenter
            = VehicleActivityPresenterImpl()

}