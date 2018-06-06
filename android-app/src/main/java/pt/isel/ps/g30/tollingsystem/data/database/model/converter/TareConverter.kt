package pt.isel.ps.g30.tollingsystem.data.database.model.converter


import androidx.room.TypeConverter

import pt.isel.ps.g30.tollingsystem.data.api.model.Tare

class TareConverter {

    @TypeConverter
    fun fromName(value: String): Tare = Tare.valueOf(value)


    @TypeConverter
    fun tareToName(tare: Tare): String = tare.name

}
