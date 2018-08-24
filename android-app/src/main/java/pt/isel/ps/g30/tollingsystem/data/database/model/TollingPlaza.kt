package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.TypeConverters

@Entity
data class TollingPlaza(

        val name: String,

        val concession: String,

        var active: Boolean,

        val lat: Double,

        val Lng:Double,

        val openToll: Boolean = false,

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
)