package pt.isel.ps.g30.tollingsystem.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.Notification
import pt.isel.ps.g30.tollingsystem.data.database.model.NotificationType
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters

@TypeConverters(Converters::class)
@Dao
interface NotificationDao {


    @Query("SELECT * FROM Notification WHERE id = :id")
    fun findById(id: Int): Notification

//    @Query("SELECT * FROM Notification WHERE type = :notificationType")
//    fun findByType(notificationType: NotificationType): List<Notification>

    @Query("SELECT * FROM Notification")
    fun findAll(): List<Notification>

    @Query("SELECT * FROM Notification")
    fun findAllLiveData(): LiveData<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg notifications: Notification): List<Long>

    @Delete
    fun delete(vararg notifications: Notification): Int

}
