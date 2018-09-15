package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.*

import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare

@TypeConverters(Converters::class)
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE )])
data class Vehicle(

        @PrimaryKey
        var id : Int,

        val licensePlate: String,

        val tare: Tare,

        @ColumnInfo(name = "user_id")
        val userId: Int
)