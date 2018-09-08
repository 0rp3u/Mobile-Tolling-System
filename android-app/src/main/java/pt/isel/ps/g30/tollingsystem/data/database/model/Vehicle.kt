package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.TypeConverters

import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare

@TypeConverters(Converters::class)
@Entity
data class Vehicle(

        @PrimaryKey
        var id : Int,

        val licensePlate: String,

        val tare: Tare,

        val userId: Int
)