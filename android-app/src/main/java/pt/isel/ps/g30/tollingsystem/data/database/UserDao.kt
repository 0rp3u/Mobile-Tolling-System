package pt.isel.ps.g30.tollingsystem.data.database

import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.User

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
