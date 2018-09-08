package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.UnvalidatedTransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

@Dao
interface TemporaryTransactionDao {

    @Query("SELECT * FROM UnvalidatedTransactionInfo WHERE UnvalidatedTransactionInfo.id = :id")
    fun find(id: Int): UnvalidatedTransactionInfo?

    @Query("SELECT * FROM UnvalidatedTransactionInfo WHERE UnvalidatedTransactionInfo.closed = 0")
    fun findToClose(): UnvalidatedTransactionInfo?

    @Query("SELECT * FROM UnvalidatedTransactionInfo WHERE UnvalidatedTransactionInfo.closed = 0")
    fun findToCloseData(): LiveData<UnvalidatedTransactionInfo>

    @Query("SELECT Vehicle.* FROM Vehicle, UnvalidatedTransactionInfo WHERE UnvalidatedTransactionInfo.closed = 0 and UnvalidatedTransactionInfo.vehicle_id = Vehicle.id")
    fun findActiveVehicle(): Vehicle?

    @Update
    fun update(active :UnvalidatedTransactionInfo): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(active: UnvalidatedTransactionInfo): Long

    @Delete
    fun delete(active :UnvalidatedTransactionInfo): Int

}
