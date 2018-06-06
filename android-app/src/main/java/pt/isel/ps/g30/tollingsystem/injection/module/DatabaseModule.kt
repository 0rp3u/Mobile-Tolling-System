package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Provides
import javax.inject.Singleton
import dagger.Module
import androidx.room.Room
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.TollingPlazaDao
import pt.isel.ps.g30.tollingsystem.data.database.insertTestdata


@Module
class DatabaseModule {

    //@Provides
    @Singleton
    fun providesRoomDatabase(app: TollingSystemApp): TollingSystemDatabase
        = Room.databaseBuilder(app, TollingSystemDatabase::class.java, "tolling-db").build().also { insertTestdata(it) }

    @Provides
    @Singleton
    fun providesInMemoryRoomDatabase(app: TollingSystemApp): TollingSystemDatabase
            = Room.inMemoryDatabaseBuilder(app, TollingSystemDatabase::class.java).build().also { insertTestdata(it) }


    @Provides
    @Singleton
    fun providesTollingDao(tollingSystemDatabase: TollingSystemDatabase): TollingPlazaDao = tollingSystemDatabase.TollingDao()

}