package pt.isel.ps.g30.tollingsystem.data.database


import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

@Dao
interface VehicleDao {

    @Query("SELECT * FROM Vehicle")
    fun findAll(): List<Vehicle>

    @Query("SELECT * FROM Vehicle WHERE id = :id")
    fun findById(id: Int): Vehicle

    @Query("SELECT * FROM Vehicle WHERE ownerId = :ownerId")
    fun findByOwner(ownerId: Int): List<Vehicle>

    @Query("SELECT Vehicle.* FROM Vehicle, ActiveTrip WHERE ActiveTrip.vehicle_id = Vehicle.id")
    fun findActive(): Vehicle?

    @Update()
    fun update(vehicle: Vehicle): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg vehicles:Vehicle): List<Long>

    @Delete
    fun delete(vehicle: Vehicle): Int

}
