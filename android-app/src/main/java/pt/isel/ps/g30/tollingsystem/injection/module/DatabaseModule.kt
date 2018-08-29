package pt.isel.ps.g30.tollingsystem.injection.module

import android.content.Context
import dagger.Provides
import javax.inject.Singleton
import dagger.Module
import androidx.room.Room
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.TollingPlazaDao
import pt.isel.ps.g30.tollingsystem.data.database.insertTestdata
import javax.inject.Named


@Module
class DatabaseModule {

    @Provides
    @Singleton
    @Named("disk")
    fun providesRoomDatabase(app: Context): TollingSystemDatabase
        = Room.databaseBuilder(app, TollingSystemDatabase::class.java, "tolling-db").build()

    @Provides
    @Singleton
    @Named("memory")
    fun providesInMemoryRoomDatabase(app: Context): TollingSystemDatabase
            = Room.inMemoryDatabaseBuilder(app, TollingSystemDatabase::class.java).build()


    @Provides
    @Singleton
    fun providesRoomDatabaseWithData(@Named("memory") database: TollingSystemDatabase): TollingSystemDatabase
            =database.apply { /*insertTestdata(this)*/ }


}