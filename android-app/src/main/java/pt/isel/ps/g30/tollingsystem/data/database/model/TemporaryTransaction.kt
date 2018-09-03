package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*


@TypeConverters(Converters::class)
@Entity
data class TemporaryTransaction(

        @Embedded(prefix = "vehicle_")
        var vehicle: Vehicle? = null,

        @Embedded(prefix = "origin_")
        var origin: TollingPassage? = null,

        @Embedded(prefix = "destination_")
        var destination: TollingPassage? = null,

        var clean :Boolean = true,

        @PrimaryKey(autoGenerate = true)
        var id: Int=0
)