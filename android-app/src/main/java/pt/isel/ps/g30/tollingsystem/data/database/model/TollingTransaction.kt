package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.TypeConverters
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*


@TypeConverters(Converters::class)
@Entity
data class TollingTransaction(

        @PrimaryKey
        var id: Int=-1,

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