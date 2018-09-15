package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (

        @PrimaryKey
        val id: Int,

        val name: String,

        val login: String,

        var current: Boolean = false
)
