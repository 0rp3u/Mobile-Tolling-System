package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*

@TypeConverters(Converters::class)
@Entity
data class Notification(

        val type: NotificationType,

        @Embedded(prefix = "vehicle_")
        var vehicle: Vehicle? = null,

        @Embedded(prefix = "trip_")
        var trip: TollingTrip? = null,

        var Timestamp: Date = Date(),

        @PrimaryKey(autoGenerate = true)
        var id: Int=0
)
