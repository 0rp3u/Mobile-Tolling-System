package pt.isel.ps.g30.tollingsystem.data.database

import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.User
import java.util.*

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE id=:id")
    fun findById(id: Int): User?


    @Query("SELECT * FROM User WHERE login = :login")
    fun findByLogin(login:String): User?


    @Query("SELECT * FROM User WHERE User.current = 1")
    fun findCurrent(): User?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user: User): List<Long>

    @Update
    fun update(vararg user: User):  Int

    @Delete
    fun delete(user: User): Int

}
