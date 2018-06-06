package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.TypeConverters

@Entity
data class TollingTrip(

        //val timestamp: Timestamp, TODO

        @Embedded(prefix = "vehicle_")
        val vehicle: Vehicle,

        @Embedded(prefix = "origin_")
        val origin: TollingPlaza,

        @Embedded(prefix = "destination_")
        var destination: TollingPlaza? = null,

        var paid: Boolean = false,

        @PrimaryKey(autoGenerate = true)
        var id: Int=0
)