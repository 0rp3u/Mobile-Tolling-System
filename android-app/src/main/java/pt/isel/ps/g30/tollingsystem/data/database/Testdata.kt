package pt.isel.ps.g30.tollingsystem.data.database

import android.util.Log
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare
import pt.isel.ps.g30.tollingsystem.data.database.model.*

fun insertTestdata(database: TollingSystemDatabase){

    launch{
        database.UserDao().insert(User("david","user1", "pass"))
        database.VehicleDao().insert(
                Vehicle( "14-AR-43", 1, Tare.Classe_1, true),
                Vehicle( "44-EW-82", 1, Tare.Classe_2),
                Vehicle( "17-AC-19", 1, Tare.Classe_3),
                Vehicle( "76-CC-63", 1, Tare.Classe_4),
                Vehicle( "05-VW-59", 1, Tare.Classe_5)
        )

        database.TollingDao().insert(
                TollingPlaza( "palmela", "brisa", true, 38.584453, -8.888651),
                TollingPlaza( "Ponte 25 de abril", "lusoponte", true, 38.675975, -9.173930),
                TollingPlaza( "coina", "brisa", true, 38.579182, -9.013340),
                TollingPlaza( "fofinhas da dança", "ESD", true, 38.7564632,-9.1156641),
                TollingPlaza( "fofinhas de química", "ISEL", true, 38.7568957,-9.1168083)
        )


        database.TollingTripDao().insert(
                TollingTrip(  database.VehicleDao().findById(1), database.TollingDao().findById(1), database.TollingDao().findById(3)),
                TollingTrip( database.VehicleDao().findById(2), database.TollingDao().findById(2), database.TollingDao().findById(1)),
                TollingTrip(  database.VehicleDao().findById(2), database.TollingDao().findById(2), database.TollingDao().findById(2), true),
                TollingTrip(  database.VehicleDao().findById(4), database.TollingDao().findById(1), database.TollingDao().findById(3)),
                TollingTrip( database.VehicleDao().findById(1), database.TollingDao().findById(1), database.TollingDao().findById(2)),
                TollingTrip( database.VehicleDao().findById(2), database.TollingDao().findById(3), database.TollingDao().findById(1)),
                TollingTrip(  database.VehicleDao().findById(1), database.TollingDao().findById(1), database.TollingDao().findById(1), true)
        )

//        database.TollingTripDao().insert( //active
//                TollingTrip(  database.VehicleDao().findById(1), database.TollingDao().findById(2), null)
//
//        )
    }
}