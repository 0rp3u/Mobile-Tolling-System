package pt.isel.ps.g30.tollingsystem.extension

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun LatLng.haversineDistance(otherPoint: LatLng): Float {

    val results = FloatArray(1)
    Location.distanceBetween(
            otherPoint.latitude, otherPoint.longitude,
            latitude, longitude, results)

    return results[0]

//    val λ1 = toRadians(latitude)
//    val λ2 = toRadians(longitude)
//    val Δλ = toRadians(otherPoint.latitude - latitude)
//    val Δφ = toRadians(otherPoint.longitude - otherPoint.longitude)
//    return 2 * 6371000 * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2))) //6372.8 -> radius of the earth in km
}
