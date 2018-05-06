package pt.isel.ps.g30.tollingsystem.injection.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import pt.isel.ps.g30.tollingsystem.api.TollingService
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractor
import pt.isel.ps.g30.tollingsystem.interactor.auth.AuthInteractorImpl
import pt.isel.ps.g30.tollingsystem.interceptor.HttpAuthInterceptor
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenter
import pt.isel.ps.g30.tollingsystem.presenter.login.LoginPresenterImp
import pt.isel.ps.g30.tollingsystem.presenter.login.SplashPresenter
import pt.isel.ps.g30.tollingsystem.presenter.login.SplashPresenterImp


@Module
class AuthModule {

    @Provides
    fun provideAuthInteractor(tollingService: TollingService, authInterceptor: HttpAuthInterceptor): AuthInteractor
            =AuthInteractorImpl(tollingService,authInterceptor)

    @Provides
    fun provideLoginPresenter(interactor: AuthInteractor, sharedPreferences: SharedPreferences):LoginPresenter
            =LoginPresenterImp(interactor, sharedPreferences)

    @Provides
    fun provideSplashPresenter(interactor: AuthInteractor, sharedPreferences: SharedPreferences): SplashPresenter
            = SplashPresenterImp(interactor, sharedPreferences)

}