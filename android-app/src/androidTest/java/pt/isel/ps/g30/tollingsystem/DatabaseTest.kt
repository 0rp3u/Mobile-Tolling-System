package pt.isel.ps.g30.tollingsystem

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pt.isel.ps.g30.tollingsystem.data.api.model.Tare
import pt.isel.ps.g30.tollingsystem.data.database.model.*
import java.util.*

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    lateinit var app: TollingSystemApp


    @Before
    fun dependencies(){
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        app = instrumentation.targetContext.applicationContext as TollingSystemApp

    }


    @Test
    fun userTest(){
        val database = provideDatabase(app)
        val users = arrayOf(
                User("name_1", "login_1", "password_1"),
                User("name_2", "login_2", "password_2"),
                User("name_3", "login_3", "password_3"),
                User("name_4", "login_4", "password_4"),
                User("name_5", "login_5", "password_5"),
                User("name_6", "login_6", "password_6"))

        val inserted = database.UserDao().insert(*users)

        assert(inserted.size == 6)

        val user2 :User = database.UserDao().findById(1)!! //<- test's if is not null :)

        assert(user2.id == 1)
        assert(user2.login == users[0].login)
        assert(user2.name == users[0].name)
        assert(user2.password == users[0].password)

        var user4 = database.UserDao().findByLogin(users[3].login)!!

        assert(user4 == users[3])

        val deletedNum = database.UserDao().delete(user4)

        assert(deletedNum == 1)


        assert(database.UserDao().findByLogin(users[3].login) == null)

        database.clearAllTables()

        val someUser = database.UserDao().findById(1)

        assert(someUser == null)

        database.close()

        assert(!database.isOpen)
    }




    @Test
    fun vehicleTest(){
        val database = provideDatabase(app)
        val users = arrayOf(User("name_1", "login_1", "password_1"),
                User("name_2", "login_2", "password_2"),
                User("name_3", "login_3", "password_3")
        )

        database.UserDao().insert(*users)

        val user0 :User = database.UserDao().findById(1)!!

        val user2 = database.UserDao().findByLogin(users[1].login)!!

        val vehicles = arrayOf(
                Vehicle("10-aa-10", user0.id, Tare.Classe_1),
                Vehicle("10-aa-10", user0.id, Tare.Classe_2),
                Vehicle("10-aa-10", user0.id, Tare.Classe_3),
                Vehicle("10-aa-10", user2.id, Tare.Classe_1),
                Vehicle("10-aa-10", user2.id, Tare.Classe_5))

        val inserted = database.VehicleDao().insert(*vehicles)

        assert(inserted.size == 5)

        assert(database.VehicleDao().findActive() == vehicles[1])

        database.close()

        assert(database.isOpen.not())

    }


    @Test
    fun plazaTest(){
        val database = provideDatabase(app)

        val plazas = arrayOf(
                TollingPlaza( "palmela", "brisa", true, 38.584453, -8.888651),
                TollingPlaza( "Ponte 25 de abril", "lusoponte", true, 38.675975, -9.173930, true),
                TollingPlaza( "coina", "brisa", true, 38.579182, -9.013340),
                TollingPlaza( "fofinhas da dança", "ESD", true, 38.7564632,-9.1156641),
                TollingPlaza( "fofinhas de química", "ISEL", true, 38.7568957,-9.1168083)
        )

        database.TollingDao().insert(*plazas)


        val plaza3 = database.TollingDao().findById(2)

        assert(plazas[2].id == plaza3.id)

        plaza3.active = false

        val updatedNum = database.TollingDao().update(plaza3)

        assert(updatedNum == 1)

        val active = database.TollingDao().findActive()

        assert(active.contains(plaza3).not())

        val all = database.TollingDao().findAll()

        assert(all.size == 5)

        database.close()

        assert(database.isOpen.not())

    }


    @Test
    fun tripTest(){
        val database = provideDatabase(app)

        val plazas = arrayOf(
                TollingPlaza( "palmela", "brisa", true, 38.584453, -8.888651),
                TollingPlaza( "Ponte 25 de abril", "lusoponte", true, 38.675975, -9.173930,true),
                TollingPlaza( "coina", "brisa", true, 38.579182, -9.013340),
                TollingPlaza( "fofinhas da dança", "ESD", true, 38.7564632,-9.1156641),
                TollingPlaza( "fofinhas de química", "ISEL", true, 38.7568957,-9.1168083)
        )

        val insertedPlazas = database.TollingDao().insert(*plazas)


        assert(insertedPlazas.size == 5)

        val users = arrayOf(
                User("name_1", "login_1", "password_1"),
                User("name_2", "login_2", "password_2"),
                User("name_3", "login_3", "password_3")
        )

        val insertedUsers = database.UserDao().insert(*users)

        assert(insertedUsers.size == 3)



        val user0 :User = database.UserDao().findById(1)!!

        val user2 = database.UserDao().findByLogin(users[1].login)!!

        val vehicles = arrayOf(
                Vehicle("10-aa-10", user0.id, Tare.Classe_1),
                Vehicle("20-aa-20", user0.id, Tare.Classe_2),
                Vehicle("30-aa-30", user0.id, Tare.Classe_3),
                Vehicle("40-ba-40", user2.id, Tare.Classe_1),
                Vehicle("50-ba-50", user2.id, Tare.Classe_5))

        val insertedVehicles = database.VehicleDao().insert(*vehicles)

        assert(insertedVehicles.size == 5)


        val trips = arrayOf(
                TollingTransaction(  database.VehicleDao().findById(1), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3)),
                TollingTransaction( database.VehicleDao().findById(2), database.TollingDao().findById(1), Date(), database.TollingDao().findById(4)),
                TollingTransaction(  database.VehicleDao().findById(2), database.TollingDao().findById(2), Date(), database.TollingDao().findById(2), Date(), true),
                TollingTransaction(  database.VehicleDao().findById(4), database.TollingDao().findById(1), Date(), database.TollingDao().findById(3)),
                TollingTransaction( database.VehicleDao().findById(1), database.TollingDao().findById(2), Date(), database.TollingDao().findById(2)),
                TollingTransaction( database.VehicleDao().findById(2), database.TollingDao().findById(3), Date(), database.TollingDao().findById(1)),
                TollingTransaction(  database.VehicleDao().findById(1), database.TollingDao().findById(1), Date(), database.TollingDao().findById(1), Date(),true)
        )

        val insertedTrips = database.TollingTripDao().insert(*trips)

        assert(insertedTrips.size == 7)

        val active =  database.ActiveTripDao().findLiveData()

        var iter = 0
        active.observeForever {
            when(iter){
                0-> assert(it?.vehicle != null && it.origin == null)
                1-> assert(it?.origin != null)
                2-> assert(it?.origin == null)
            }
            iter++
        }

        //add vehicle
        database.ActiveTripDao().insert(CurrentTransaction(database.VehicleDao().findById(1)))

        val activeVehicle = database.ActiveTripDao().findActiveVehicle()

        assert(activeVehicle?.id == 1)


        val currentTrip = database.ActiveTripDao().find()

        currentTrip.origin = database.TollingDao().findById(3)
        currentTrip.destTimestamp = Date()

        database.ActiveTripDao().update(currentTrip)


        val paid = database.TollingTripDao().findPaid()

        assert(paid.size == 2)
        assert(paid[0].id == 2)
        assert(paid[1].id == 6)




        val initiateTrip = TollingTransaction( database.VehicleDao().findById(1), database.TollingDao().findById(1), Date())


        val initiated = database.TollingTripDao().insert(initiateTrip)

        assert(initiated.size == 1)

        val all = database.TollingTripDao().findAll()

        val newActive = database.TollingTripDao().findByActiveTrip()!!

        val dest = database.TollingDao().findActive()[2]

        val deferred = CompletableDeferred<Boolean>()
        runBlocking (UI) {
            newActiveLive.observeForever {
                assert(it?.id == initiated[0].toInt())
                deferred.complete(it?.destination == dest)
            }
        }

        newActive.destination = dest
        newActive.destTimestamp = Date()
        database.TollingTripDao().updateTrip(newActive)

        runBlocking(UI) {
            assert(deferred.await())
        }


        val noActive = database.TollingTripDao().findByActiveTrip()

        assert(noActive == null)

        val lastone = database.TollingTripDao().findById(insertedTrips.last().toInt()+1)

        lastone.paid = true

        database.TollingTripDao().updateTrip(lastone)

        val paid2 = database.TollingTripDao().findPaid()

        assert(paid2.find { it.id == lastone.id } != null)

        database.close()

        assert(database.isOpen.not())

    }
}