package pt.isel.ps.g30.tollingsystem.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class User (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,

    var login: String,

    var password: String
)
