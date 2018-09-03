package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.TemporaryTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

@Dao
interface TemporaryTransactionDao {

    @Query("SELECT * FROM TemporaryTransaction WHERE TemporaryTransaction.id = :id")
    fun find(id: Int): TemporaryTransaction?

    @Query("SELECT * FROM TemporaryTransaction WHERE TemporaryTransaction.clean = 1")
    fun findClean(): TemporaryTransaction?

    @Query("SELECT * FROM TemporaryTransaction WHERE TemporaryTransaction.clean = 1")
    fun findCleanData(): LiveData<TemporaryTransaction>

    @Query("SELECT Vehicle.* FROM Vehicle, TemporaryTransaction WHERE TemporaryTransaction.clean = 1 and TemporaryTransaction.vehicle_id = Vehicle.id")
    fun findActiveVehicle(): Vehicle?

    @Update
    fun update(active :TemporaryTransaction): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(active: TemporaryTransaction): Long

    @Delete
    fun delete(active :TemporaryTransaction): Int

}
