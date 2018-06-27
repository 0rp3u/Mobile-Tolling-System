package pt.isel.ps.g30.tollingsystem.injection.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.geofencing.GeofencingInteractor
import pt.isel.ps.g30.tollingsystem.interactor.notification.NotificationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingplaza.TollingPlazaInteractor
import pt.isel.ps.g30.tollingsystem.interactor.tollingtrip.TollingTripInteractor
import pt.isel.ps.g30.tollingsystem.interactor.vehicle.VehicleInteractor
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenter
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.navigation.NavigationFragPresenter
import pt.isel.ps.g30.tollingsystem.presenter.navigation.NavigationFragPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenter
import pt.isel.ps.g30.tollingsystem.presenter.notification.NotificationPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.splash.SplashPresenter
import pt.isel.ps.g30.tollingsystem.presenter.splash.SplashPresenterImpl
import pt.isel.ps.g30.tollingsystem.presenter.tollingtrip.TollingTripFragPresenter
import pt.isel.ps.g30.tollingsystem.presenter.tollingtrip.TollingTripFragPresenterImpl
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
    fun provideTollingTripFragPresenter(interactor: TollingTripInteractor): TollingTripFragPresenter
            = TollingTripFragPresenterImpl(interactor)

    @Provides
    fun provideNavigationPresenter(tollingTripInteractor: TollingTripInteractor, tollingPlazaInteractor: TollingPlazaInteractor, vehicleInteractor: VehicleInteractor, geofencingInteractor: GeofencingInteractor): NavigationFragPresenter
            = NavigationFragPresenterImpl(tollingTripInteractor, tollingPlazaInteractor, vehicleInteractor, geofencingInteractor)

    @Provides
    fun provideVehicleFragPresenter(interactor: VehicleInteractor): VehiclesFragPresenter
            = VehiclesFragPresenterImpl(interactor)

    @Provides
    fun provideVehiclePresenter(interactor: VehicleInteractor, tripInteractor: TollingTripInteractor): VehiclePresenter
            = VehiclePresenterImpl(interactor, tripInteractor)

    @Provides
    fun provideNotificationPresenter(interactor: NotificationInteractor): NotificationPresenter {
        return NotificationPresenterImpl(interactor)
    }

}