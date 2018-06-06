package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.TypeConverters

import pt.isel.ps.g30.tollingsystem.data.database.model.converter.TareConverter
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare

@TypeConverters(TareConverter::class)
@Entity
data class Vehicle(

        val licensePlate: String,

        val ownerId: Int,

        val tare: Tare,

        var active: Boolean = false,

        @PrimaryKey(autoGenerate = true)
        var id : Int = 0
)