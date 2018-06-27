package pt.isel.ps.g30.tollingsystem.injection.module

import android.app.PendingIntent
import android.content.Intent
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Singleton
import dagger.Provides
import com.google.android.gms.location.GeofencingClient
import dagger.Module
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.services.GeofenceBroadcastReceiver
import pt.isel.ps.g30.tollingsystem.interactor.geofencing.GeofencingInteractor
import pt.isel.ps.g30.tollingsystem.interactor.geofencing.GeofencingInteractorImpl
import javax.inject.Named


@Module
class LocationModule {
    @Provides
    fun provideGeofencingClient(app: TollingSystemApp): GeofencingClient {
        return LocationServices.getGeofencingClient(app)
    }

    @Provides
    @Singleton
    fun provideFusedLocationClient(app: TollingSystemApp): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }


    @Provides
    @Singleton
    @Named("GeofenceBroadcastReceiver") //If we have to provide other PendingIntents we need to name the dependencies so we can differentiate between them!
    fun provideGeofenceBroadcastPendingIntent(app: TollingSystemApp): PendingIntent{
        return PendingIntent.getBroadcast(app, 0, Intent(app, GeofenceBroadcastReceiver::class.java), PendingIntent.FLAG_CANCEL_CURRENT)
    }

    @Provides
    fun provideGeofencingInteractor(@Named("GeofenceBroadcastReceiver") broadcastPendingIntent : PendingIntent, geofencingClient: GeofencingClient, tollingSystemDatabase: TollingSystemDatabase): GeofencingInteractor{
        return GeofencingInteractorImpl(broadcastPendingIntent, geofencingClient,tollingSystemDatabase )
    }
}