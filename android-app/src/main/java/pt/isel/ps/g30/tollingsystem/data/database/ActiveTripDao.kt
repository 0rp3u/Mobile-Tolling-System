package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.CurrentTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

@Dao
interface ActiveTransactionDao {

    @Query("SELECT * FROM CurrentTransaction")
    fun find(): CurrentTransaction

    @Query("SELECT * FROM CurrentTransaction")
    fun findLiveData(): LiveData<CurrentTransaction>

    @Query("SELECT Vehicle.* FROM Vehicle, CurrentTransaction WHERE CurrentTransaction.vehicle_id = Vehicle.id")
    fun findActiveVehicle(): Vehicle?

    @Update()
    fun update(active :CurrentTransaction): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(active: CurrentTransaction): Long

}
