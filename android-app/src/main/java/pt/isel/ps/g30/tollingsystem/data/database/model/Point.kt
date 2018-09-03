package pt.isel.ps.g30.tollingsystem.data.database.model


import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import pt.isel.ps.g30.tollingsystem.data.database.model.converter.Converters
import java.util.*

@TypeConverters(Converters::class)
@Entity(foreignKeys = [ForeignKey(entity = TollingPassage::class, parentColumns = ["id"], childColumns = ["passage_id"], onDelete = CASCADE )])

data class Point(

        val lat: Double,

        val Lng:Double,

        val bearing: Float,

        val timeStamp: Long = Date().time,

        @ColumnInfo(name = "passage_id")
        var PassageId: Int = -1,

        @PrimaryKey(autoGenerate = true)
        var id: Int=0
)