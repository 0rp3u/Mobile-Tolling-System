package pt.isel.ps.g30.tollingsystem.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*

@Database(entities = [User::class, TollingPlaza::class, Vehicle::class, TollingTrip::class, ActiveTrip::class], version = 1)
abstract class TollingSystemDatabase : RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun VehicleDao(): VehicleDao
    abstract fun TollingDao(): TollingPlazaDao
    abstract fun TollingTripDao(): TollingTripDao
    abstract fun ActiveTripDao(): ActiveTripDao
}
