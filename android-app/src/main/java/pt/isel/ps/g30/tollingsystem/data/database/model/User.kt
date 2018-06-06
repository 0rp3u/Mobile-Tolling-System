package pt.isel.ps.g30.tollingsystem.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (

        val name: String,

        val login: String,

        val password: String, //TODO not right, should be a token

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
)
