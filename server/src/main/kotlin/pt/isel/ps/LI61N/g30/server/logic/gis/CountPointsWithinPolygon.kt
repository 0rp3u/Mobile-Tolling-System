package pt.isel.ps.LI61N.g30.server.logic.gis

import pt.isel.ps.LI61N.g30.server.utils.GeoLocation

object CountPointsWithinPolygon{
    private val query =
            "SELECT count(*) filter(" +
                    "where ST_Intersects(" +
                    "_point," +
                    "(select _area from mts_toll where id = :_toll_id)" +
                    ")" +
                    ")" +
                    "FROM (" +
                    "select _point " +
                    "from (" +
                    "VALUES " +
                    "_points_collection" +
                    ")" +
                    "as lookup(_point)" +
                    ") as temp"

    fun getQuery(geoLocations: Array<GeoLocation>, area: Area) =
            query
                    .replace("_area", area.value)
                    .replace("_points_collection", generateQueryPoints(geoLocations))

    private fun generateQueryPoints(geoLocations: Array<GeoLocation>): String {
       val a =  geoLocations.joinToString { "(ST_SetSRID(ST_MakePoint(${it.longitude},${it.latitude}), 4326))"}//," }.dropLast(1)
        return a
    }

    enum class Area(val value: String) {
        ENTRY("entry_area"),
        EXIT("exit_area")
    }
}