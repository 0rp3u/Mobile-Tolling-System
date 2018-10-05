package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.*
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*


@TypeConverters(Converters::class)
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE )])
data class UnvalidatedTransactionInfo(

        @ColumnInfo(name = "user_id")
        val userId: Int,

        @Embedded(prefix = "vehicle_")
        var vehicle: Vehicle? = null,

        @Embedded(prefix = "origin_")
        var origin: TollingPassage? = null,

        @Embedded(prefix = "destination_")
        var destination: TollingPassage? = null,

        var closed :Boolean = false,

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
)