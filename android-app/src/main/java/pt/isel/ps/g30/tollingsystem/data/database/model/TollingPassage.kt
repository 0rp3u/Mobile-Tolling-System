package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*


@TypeConverters(Converters::class)
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE )])
data class TollingPassage(

        @ColumnInfo(name = "user_id")
        val userId: Int,

        @Embedded(prefix = "vehicle_")
        val vehicle: Vehicle,

        @Embedded(prefix = "plaza_")
        val plaza: TollingPlaza,

        val timestamp: Date =  Date(),

        @ColumnInfo(name = "transaction_id")
        var TransactionId: Int = -1,


        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
)
