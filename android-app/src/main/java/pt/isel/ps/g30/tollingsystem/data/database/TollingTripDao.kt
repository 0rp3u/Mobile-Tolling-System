package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip

@Dao
interface TollingTripDao {


    @Query("SELECT * FROM TollingTrip")
    fun findAll(): List<TollingTrip>

    @Query("SELECT * FROM TollingTrip WHERE id = :id")
    fun findById(id: Int): TollingTrip?

    @Query("SELECT * FROM TollingTrip WHERE paid is not null")
    fun findPaid(): List<TollingTrip>

    @Query("SELECT * FROM TollingTrip WHERE TollingTrip.vehicle_id = :vehicleId")
    fun findByVehicle(vehicleId: Int): List<TollingTrip>

    @Query("SELECT * FROM TollingTrip WHERE TollingTrip.destination_id is null")
    fun findByActiveTripLiveData(): LiveData<TollingTrip?>


    @Query("SELECT * FROM TollingTrip WHERE TollingTrip.destination_id is null")
    fun findByActiveTrip(): TollingTrip?



    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTrip(trip: TollingTrip)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg trips: TollingTrip): List<Long>

    @Delete
    fun delete(plaza: TollingTrip): Int

}
