package pt.isel.ps.g30.tollingsystem.data.database

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import java.util.*

fun insertTestdata(database: TollingSystemDatabase){

    launch{
        database.UserDao().findCurrent() ?: database.UserDao().insert(User(200,"david","orpheu", true))
//        database.VehicleDao().insert(
//                Vehicle(1, "14-AR-43",  Tare.Classe_1, 200),
//                Vehicle(2,"44-EW-82",  Tare.Classe_2,200),
//                Vehicle(3, "17-AC-19",  Tare.Classe_3,200),
//                Vehicle(4, "76-CC-63",  Tare.Classe_4,200),
//                Vehicle(5, "05-VW-59",  Tare.Classe_5,200)
//        )
//
//        database.TollingDao().insert(
//                TollingPlaza(1, "palmela", "brisa", true, 38.584453, -8.888651),
//                TollingPlaza(2, "Ponte 25 de abril", "lusoponte", true, 38.675975, -9.173930, true),
//                TollingPlaza(3, "coina", "brisa", true, 38.579182, -9.013340),
//                TollingPlaza(4, "dança", "ESD", true, 38.7564632,-9.1156641),
//                TollingPlaza(5, "química", "ISEL", true, 38.7568957,-9.1168083)
//        )
////
////
//        database.TollingTransactionDao().insert(
//                TollingTransaction(1, 200, database.VehicleDao().findById(1), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3),Date(),69.23),
//                TollingTransaction(2,200,database.VehicleDao().findById(2), database.TollingDao().findById(3), Date(), database.TollingDao().findById(1),Date(),9.23),
//                TollingTransaction(3,200, database.VehicleDao().findById(2), database.TollingDao().findById(2), Date(), database.TollingDao().findById(2), Date(), 2.56),
//                TollingTransaction(4,200, database.VehicleDao().findById(4), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3)),
//                TollingTransaction(5,200, database.VehicleDao().findById(1), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3)),
//                TollingTransaction(6,200,database.VehicleDao().findById(2), database.TollingDao().findById(3), Date(), database.TollingDao().findById(1)),
//                TollingTransaction(7,200, database.VehicleDao().findById(1), database.TollingDao().findById(2), Date(), database.TollingDao().findById(2),  Date(),29.23)
//        )
//
//         //if there is no current transaction create one.
//            if(database.ActiveTransactionDao().findToClose() == null)
//                database.ActiveTransactionDao().insert(UnvalidatedTransactionInfo(200))
//
//        database.NotificationDao().insert(Notification(NotificationType.TransactionNotification,200, transaction =database.TollingTransactionDao().findById(1)))
//
//        database.NotificationDao().insert(Notification(NotificationType.VehicleAddedNotification,200, vehicle = database.VehicleDao().findById(4)))
//
//        launch {
//            delay(5000)
//            database.NotificationDao().insert(Notification(NotificationType.TransactionNotification,200, transaction =database.TollingTransactionDao().findById(2)))
//        }
    }
}