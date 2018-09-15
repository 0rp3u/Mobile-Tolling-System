package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*


@TypeConverters(Converters::class)
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE )])
data class TollingTransaction(

        @PrimaryKey
        var id: Int=-1,

        @ColumnInfo(name = "user_id")
        val userId: Int,

        @Embedded(prefix = "vehicle_")
        val vehicle: Vehicle,

        @Embedded(prefix = "origin_")
        val origin: TollingPlaza,

        val originTimestamp: Date =  Date(),

        @Embedded(prefix = "destination_")
        val destination: TollingPlaza,

        val destTimestamp: Date =  Date(),

        var paid: Double? = null
)