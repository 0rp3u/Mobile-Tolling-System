package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

@Dao
interface VehicleDao {

    @Query("SELECT Vehicle.* FROM Vehicle, User where Vehicle.user_id = User.id and User.current = 1")
    fun findAll(): List<Vehicle>

    @Query("SELECT Vehicle.* FROM Vehicle, User where Vehicle.user_id = User.id and User.current = 1")
    fun findAllLiveData(): LiveData<List<Vehicle>>

    @Query("SELECT * FROM Vehicle WHERE id = :id")
    fun findById(id: Int): Vehicle

    @Query("SELECT Vehicle.* FROM Vehicle, User, UnvalidatedTransactionInfo WHERE Vehicle.user_id = User.id and User.current = 1 and UnvalidatedTransactionInfo.vehicle_id = Vehicle.id")
    fun findActive(): Vehicle?

    @Update()
    fun update(vehicle: Vehicle): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg vehicles:Vehicle): List<Long>

    @Delete
    fun delete(vehicle: Vehicle): Int

}
