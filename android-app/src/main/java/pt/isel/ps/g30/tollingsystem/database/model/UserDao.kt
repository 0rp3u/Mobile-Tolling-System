package pt.isel.ps.g30.tollingsystem.database.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE id=:id")
    fun findById(id: Int): User


    @Query("SELECT * FROM user WHERE login = :login")
    fun findByLogin(login:String): User


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Delete
    fun delete(user: User): Int

}
