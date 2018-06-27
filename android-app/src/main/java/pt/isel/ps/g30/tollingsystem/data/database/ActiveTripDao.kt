package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.ActiveTrip
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.data.database.model.Vehicle

@Dao
interface ActiveTripDao {

    @Query("SELECT * FROM ActiveTrip")
    fun find(): ActiveTrip

    @Query("SELECT * FROM ActiveTrip")
    fun findLiveData(): LiveData<ActiveTrip>

    @Query("SELECT Vehicle.* FROM Vehicle, ActiveTrip WHERE ActiveTrip.vehicle_id = Vehicle.id")
    fun findActiveVehicle(): Vehicle?

    @Update()
    fun update(active :ActiveTrip): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(active: ActiveTrip): Long

}
