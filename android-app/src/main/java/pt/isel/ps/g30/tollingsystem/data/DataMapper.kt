package pt.isel.ps.g30.tollingsystem.data

import com.google.android.gms.maps.model.LatLng
import pt.isel.ps.g30.tollingsystem.data.api.model.TransactionInfo
import pt.isel.ps.g30.tollingsystem.data.api.model.Point as APiPoint
import pt.isel.ps.g30.tollingsystem.data.database.model.Point
import pt.isel.ps.g30.tollingsystem.data.database.model.UnvalidatedTransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPassage
import pt.isel.ps.g30.tollingsystem.extension.dateTimeParsed

fun Point.mapForApi() = APiPoint(
        LatLng(lat, Lng),
        bearing,
        accuracy,
        this.timeStamp
)


fun UnvalidatedTransactionInfo.mapForApi(openPoints:List<Point>, closePoints: List<Point>) = TransactionInfo(
        vehicle!!.id,
        origin!!.plaza!!.id,
        origin!!.timestamp.dateTimeParsed(),
        openPoints.map { it.mapForApi() },
        destination!!.plaza!!.id,
        destination!!.timestamp.dateTimeParsed(),
       closePoints.map { it.mapForApi() }
)

