package pt.isel.ps.g30.tollingsystem.database.model

import android.arch.persistence.room.PrimaryKey


data class TollingPlaza(

        @PrimaryKey(autoGenerate = true)
        val id: Int,

        val name: String,

        val latLng: String //TODO create latlong model
)