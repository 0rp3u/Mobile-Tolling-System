package pt.isel.ps.LI61N.g30.server.logic

object GetNClosestTolls{
    val MAX_TOLLS: Int = 90

    val query =
            "select mts_toll.id\n" +
                    "FROM mts_toll\n" +
                    "order by st_distance(\n" +
                    "ST_Transform(ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), 2100),\n" +
                    "ST_Transform(geolocation, 2100)\n" +
                    ") asc limit :n"
}