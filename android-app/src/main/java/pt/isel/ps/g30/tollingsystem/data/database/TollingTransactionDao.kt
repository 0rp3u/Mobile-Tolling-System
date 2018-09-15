package pt.isel.ps.g30.tollingsystem.data.database


import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTransaction

@Dao
interface TollingTransactionDao {


    @Query("SELECT TollingTransaction.* FROM TollingTransaction, User where TollingTransaction.user_id = User.id and User.current = 1")
    fun findAll(): List<TollingTransaction>

    @Query("SELECT * FROM TollingTransaction WHERE id = :id")
    fun findById(id: Int): TollingTransaction?

    @Query("SELECT TollingTransaction.* FROM TollingTransaction, User WHERE TollingTransaction.user_id = User.id and User.current = 1 and paid is not null")
    fun findPaid(): List<TollingTransaction>

    @Query("SELECT TollingTransaction.* FROM TollingTransaction WHERE TollingTransaction.vehicle_id = :vehicleId")
    fun findByVehicle(vehicleId: Int): List<TollingTransaction>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(transaction: TollingTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg transactions: TollingTransaction): List<Long>

    @Delete
    fun delete(plaza: TollingTransaction): Int

}
