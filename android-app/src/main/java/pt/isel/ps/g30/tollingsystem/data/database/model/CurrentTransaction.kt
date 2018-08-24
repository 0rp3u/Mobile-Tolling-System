package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*


@TypeConverters(Converters::class)
@Entity
data class CurrentTransaction(

        @Embedded(prefix = "vehicle_")
        var vehicle: Vehicle? = null,

        @Embedded(prefix = "origin_")
        var origin: TollingPlaza? = null,

        var originTimestamp: Date? = null,

        @Embedded(prefix = "destination_")
        var destination: TollingPlaza? = null,

        var destTimestamp: Date? = null,

        @PrimaryKey
        var id: Int=1
)