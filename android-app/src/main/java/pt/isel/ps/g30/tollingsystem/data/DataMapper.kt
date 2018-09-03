package pt.isel.ps.g30.tollingsystem.data

import com.google.android.gms.maps.model.LatLng
import pt.isel.ps.g30.tollingsystem.data.api.model.TollPassageInfo
import pt.isel.ps.g30.tollingsystem.data.api.model.TransactionInfo
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.api.model.Point as APiPoint
import pt.isel.ps.g30.tollingsystem.data.database.model.Point
import pt.isel.ps.g30.tollingsystem.data.database.model.TemporaryTransaction
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPassage

fun Point.mapForApi() = APiPoint(
        LatLng(lat, Lng),
        bearing,
        this.timeStamp
)

fun TollingPassage.mapForApi(points: List<Point>) = TollPassageInfo(
       plaza.id,
        points.map { it.mapForApi() }
)

fun TemporaryTransaction.mapForApi(openPoints:List<Point>, closePoints: List<Point>) = TransactionInfo(
        vehicle!!.id,
        origin!!.mapForApi(openPoints),
        destination!!.mapForApi(closePoints)
)

