package pt.isel.ps.g30.tollingsystem.injection.module

import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import dagger.Module
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.database.DemoDatabase
import pt.isel.ps.g30.tollingsystem.database.model.UserDao


@Module
class RoomModule {

    @Singleton
    @Provides
    internal fun providesRoomDatabase(app: TollingSystemApp): DemoDatabase {
        return Room.databaseBuilder(app, DemoDatabase::class.java, "tolling-db").build()
    }

    @Singleton
    @Provides
    internal fun providesProductDao(demoDatabase: DemoDatabase): UserDao {
        return demoDatabase.UserDao()
    }

}