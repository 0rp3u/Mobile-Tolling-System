package pt.isel.ps.g30.tollingsystem.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.*

@Database(entities = [User::class, TollingPlaza::class, Vehicle::class, TollingTransaction::class, UnvalidatedTransactionInfo::class, Notification::class, TollingPassage::class, Point::class], version = 1)
abstract class TollingSystemDatabase : RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun VehicleDao(): VehicleDao
    abstract fun TollingDao(): TollingPlazaDao
    abstract fun TollingTransactionDao(): TollingTransactionDao
    abstract fun ActiveTransactionDao(): TemporaryTransactionDao
    abstract fun NotificationDao(): NotificationDao
    abstract fun TollingPassageDao(): TollingPassageDao
}
