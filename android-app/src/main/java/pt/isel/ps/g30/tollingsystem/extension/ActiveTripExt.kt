package pt.isel.ps.g30.tollingsystem.extension

import pt.isel.ps.g30.tollingsystem.data.database.model.ActiveTrip
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingTrip

fun ActiveTrip.toTollingTrip(): TollingTrip{
    if(vehicle == null || destination == null || destTimestamp == null || origin ==null || originTimestamp == null) throw Exception(" could not convert to tolling trip, active trip is not finished")

    return TollingTrip(vehicle!!, origin!!, originTimestamp!!, destination!!, destTimestamp!!)

}