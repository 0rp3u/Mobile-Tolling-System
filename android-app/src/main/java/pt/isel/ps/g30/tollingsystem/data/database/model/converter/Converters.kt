package pt.isel.ps.g30.tollingsystem.data.database.model.converter


import androidx.room.TypeConverter

import pt.isel.ps.g30.tollingsystem.data.api.model.Tare
import pt.isel.ps.g30.tollingsystem.data.database.model.NotificationType
import java.util.*


class Converters {

    @TypeConverter
    fun fromName(value: String): Tare = Tare.valueOf(value)


    @TypeConverter
    fun tareToName(tare: Tare): String = tare.name


    @TypeConverter
    fun fromTimestamp(value: Long): Date  = Date(value)


    @TypeConverter
    fun dateToTimestamp(date: Date?): Long =
            date?.time ?: System.currentTimeMillis()

    @TypeConverter
    fun fromNametoNotification(value: String): NotificationType = NotificationType.valueOf(value)


    @TypeConverter
    fun NotificationToName(type: NotificationType): String = type.name

}
