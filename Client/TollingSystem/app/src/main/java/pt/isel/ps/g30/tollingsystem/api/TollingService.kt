package pt.isel.ps.g30.tollingsystem.api

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.model.User
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import retrofit2.http.*

interface TollingService {

    //TODO user interceptor for authorization header, https://github.com/square/okhttp/wiki/Interceptors

    @GET("plazas/{position}")
    fun getNearPlazas(position : String): Deferred<List<TollingPlaza>>

    @POST("user/new")
    fun createUser(@Body user: User): Deferred<User>

    @GET("vehicle")
    fun getVehicleList(@Header("Authorization") authorization: String): Deferred<List<Vehicle>>

    @PUT("vehicle")
    fun addVehicle(@Header("Authorization") authorization: String, @Body vehicle: Vehicle): Deferred<Vehicle>

    @GET("vehicle/{id}")
    fun getVehicleDetails(@Header("Authorization") authorization: String,@Path("id") id: Int): Deferred<Vehicle>

    @GET("vehicle/{id}/transaction")
    fun getVehicleTransactions(@Header("Authorization") authorization: String,@Path("id") id: Int): Deferred<List<TollingTransaction>>

    @GET("vehicle/{id}/transaction/open")
    fun getVehicleOpenTransactions(@Header("Authorization") authorization: String,@Path("id") id: Int):Deferred<List<TollingTransaction>>

    @GET("vehicle/{id}/transaction/closed")
    fun getVehicleCLosedTransactions(@Header("Authorization") authorization: String,@Path("id") id: Int): Deferred<List<TollingTransaction>>

    @GET("transaction")
    fun getTransactionList(@Header("Authorization") authorization: String,@Query("owner") owner: String): Deferred<List<TollingTransaction>>

    @GET("transaction/closed")
    fun getClosedTransactionList(@Header("Authorization") authorization: String,@Query("owner") owner: String): Deferred<List<TollingTransaction>>

    @GET("transaction/open")
    fun getOpenTransactionList(@Header("Authorization") authorization: String): Deferred<List<TollingTransaction>>

    @GET("transaction/{id}")
    fun getTransactionDetails(@Header("Authorization") authorization: String, @Path("id") id: Int): Deferred<TollingTransaction>

    @POST("transaction/new")
    fun initiateTollingTransaction(@Header("Authorization") authorization: String,@Body transaction: TollingTransaction): Deferred<TollingTransaction>

    @PUT("transaction/{id}/close")
    fun closeTollingTransaction(@Header("Authorization") authorization: String, @Path("id") id: Int, @Body transaction: TollingTransaction): Deferred<TollingTransaction>



//    @POST("plaza/{id}/verify")
//    fun verifyPassage(@Header("Authorization") authorization: String, @Path("id") id: Int,@Body vector: positionsVector): Deferred<Int> TODO this should gives us a percentage of certainty that person passed a certain plaza
//

}
