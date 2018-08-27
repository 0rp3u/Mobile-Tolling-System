package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction

@Dao
interface TollingTransactionDao {


    @Query("SELECT * FROM TollingTransaction")
    fun findAll(): List<TollingTransaction>

    @Query("SELECT * FROM TollingTransaction WHERE id = :id")
    fun findById(id: Int): TollingTransaction?

    @Query("SELECT * FROM TollingTransaction WHERE paid is not null")
    fun findPaid(): List<TollingTransaction>

    @Query("SELECT * FROM TollingTransaction WHERE TollingTransaction.vehicle_id = :vehicleId")
    fun findByVehicle(vehicleId: Int): List<TollingTransaction>

    @Query("SELECT * FROM TollingTransaction WHERE TollingTransaction.destination_id is null")
    fun findByActiveTransactionLiveData(): LiveData<TollingTransaction?>


    @Query("SELECT * FROM TollingTransaction WHERE TollingTransaction.destination_id is null")
    fun findByActiveTransaction(): TollingTransaction?



    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(transaction: TollingTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg transactions: TollingTransaction): List<Long>

    @Delete
    fun delete(plaza: TollingTransaction): Int

}
