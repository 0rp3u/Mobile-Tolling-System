package pt.isel.ps.g30.tollingsystem.data.database


import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.Point
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPassage
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza

@Dao
interface TollingPassageDao {


    @Query("SELECT * FROM TollingPassage WHERE id = :id")
    fun findById(id: Int): TollingPassage

    @Query("SELECT TollingPassage.* FROM TollingPassage, User where TollingPassage.user_id = User.id and User.current = 1")
    fun findAll (): List<TollingPassage>

    @Query("SELECT TollingPassage.* FROM TollingPassage, User where TollingPassage.user_id = User.id and User.current = 1 and TollingPassage.Transaction_id = -1 ORDER BY TollingPassage.timestamp DESC")
    fun findAllPending(): List<TollingPassage>

    @Query("SELECT * FROM Point Where Point.passage_id = :Passage_id")
    fun findPassagePoints(Passage_id: Int): List<Point>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun AddPointsToPassage(vararg points: Point): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg plazas: TollingPassage): List<Long>

    @Delete
    fun delete(vararg plazas: TollingPassage): Int

    @Update()
    fun update(vararg plazas: TollingPassage): Int

}
