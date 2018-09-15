package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*

@TypeConverters(Converters::class)
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE )])
data class Notification(

        val type: NotificationType,

        @ColumnInfo(name = "user_id")
        val userId: Int,

        @Embedded(prefix = "vehicle_")
        var vehicle: Vehicle? = null,

        @Embedded(prefix = "Transaction_")
        var transaction: TollingTransaction? = null,

        var Timestamp: Date = Date(),

        @PrimaryKey(autoGenerate = true)
        var id: Int=0
)
