package pt.isel.ps.g30.tollingsystem.data.database


import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza

@Dao
interface TollingPlazaDao {


    @Query("SELECT * FROM TollingPlaza WHERE id = :id")
    fun findById(id: Int): TollingPlaza

    @Query("SELECT * FROM TollingPlaza")
    fun findAll(): List<TollingPlaza>

    @Query("SELECT * FROM TollingPlaza WHERE active = 1")
    fun findActive(): List<TollingPlaza>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg plazas: TollingPlaza): List<Long>

    @Delete
    fun delete(vararg plazas: TollingPlaza): Int

    @Update()
    fun update(vararg plazas: TollingPlaza): Int

}
