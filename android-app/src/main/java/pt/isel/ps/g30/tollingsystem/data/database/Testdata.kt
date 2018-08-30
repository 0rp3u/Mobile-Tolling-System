package pt.isel.ps.g30.tollingsystem.data.database

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import java.util.*

fun insertTestdata(database: TollingSystemDatabase){

    launch{
//        database.ActiveTransactionDao().insert(CurrentTransaction())
//        database.UserDao().insert(User(100,"david","orpheu"))
//        database.VehicleDao().insert(
//                Vehicle(1, "14-AR-43",  Tare.Classe_1),
//                Vehicle(2,"44-EW-82",  Tare.Classe_2),
//                Vehicle(3, "17-AC-19",  Tare.Classe_3),
//                Vehicle(4, "76-CC-63",  Tare.Classe_4),
//                Vehicle(5, "05-VW-59",  Tare.Classe_5)
//        )
//
//        database.TollingDao().insert(
//                TollingPlaza(1, "palmela", "brisa", true, 38.584453, -8.888651),
//                TollingPlaza(2, "Ponte 25 de abril", "lusoponte", true, 38.675975, -9.173930, true),
//                TollingPlaza(3, "coina", "brisa", true, 38.579182, -9.013340),
//                TollingPlaza(4, "dança", "ESD", true, 38.7564632,-9.1156641),
//                TollingPlaza(5, "química", "ISEL", true, 38.7568957,-9.1168083)
//        )
//
//
//        database.TollingTransactionDao().insert(
//                TollingTransaction(  database.VehicleDao().findById(1), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3),Date(),69.23),
//                TollingTransaction( database.VehicleDao().findById(2), database.TollingDao().findById(3), Date(), database.TollingDao().findById(1),Date(),9.23),
//                TollingTransaction(  database.VehicleDao().findById(2), database.TollingDao().findById(2), Date(), database.TollingDao().findById(2), Date(), 2.56),
//                TollingTransaction(  database.VehicleDao().findById(4), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3)),
//                TollingTransaction( database.VehicleDao().findById(1), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3)),
//                TollingTransaction( database.VehicleDao().findById(2), database.TollingDao().findById(3), Date(), database.TollingDao().findById(1)),
//                TollingTransaction(  database.VehicleDao().findById(1), database.TollingDao().findById(2), Date(), database.TollingDao().findById(2),  Date(),29.23)
//        )

        database.ActiveTransactionDao().insert(CurrentTransaction())

//        database.NotificationDao().insert(Notification(NotificationType.TransactionNotification, transaction =database.TollingTransactionDao().findById(5)))
//
//        database.NotificationDao().insert(Notification(NotificationType.VehicleAddedNotification, vehicle = database.VehicleDao().findById(4)))
//
//        launch {
//            delay(5000)
//            database.NotificationDao().insert(Notification(NotificationType.TransactionNotification, transaction =database.TollingTransactionDao().findById(6)))
//        }
    }
}