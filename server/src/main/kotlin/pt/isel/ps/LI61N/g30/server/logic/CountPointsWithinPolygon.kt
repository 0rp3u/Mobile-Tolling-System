package pt.isel.ps.LI61N.g30.server.logic

import pt.isel.ps.LI61N.g30.server.model.domain.Toll
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation

object CountPointsWithinPolygon{
    val query =
            "SELECT count(*) filter(\n" +
                    "\twhere ST_Intersects(\n" +
                    "\t\t_point,\n" +
                    "\t\t(select :area from mts_toll where id = :toll_id)\n" +
                    "\t)\n" +
                    ")\n" +
                    "FROM (\n" +
                    "\tselect _point\n" +
                    "\tfrom (\n" +
                    "\t\tVALUES\n" +
                    "\t\t\t:points\n" +
                    "\t)\n" +
                    "\tas lookup(_point)\n" +
                    ") as temp"

    fun generateQueryPoints(geoLocations: Array<GeoLocation>) =
            geoLocations.joinToString { "(ST_SetSRID(ST_MakePoint(${it.longitude},${it.latitude}), 4326))," }.dropLast(1)

    enum class Area(s: String) {
        ENTRY("entry_area"),
        EXIT("exit_area")
    }
}