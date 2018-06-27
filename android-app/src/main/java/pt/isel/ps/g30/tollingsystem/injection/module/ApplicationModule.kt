package pt.isel.ps.g30.tollingsystem.injection.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: TollingSystemApp, private val context: Context? = null) {

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideContext() : Context = context ?: app

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

}
