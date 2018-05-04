package pt.isel.ps.g30.tollingsystem.api

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.model.TollingTransaction
import pt.isel.ps.g30.tollingsystem.model.User
import pt.isel.ps.g30.tollingsystem.model.Vehicle
import retrofit2.http.*

interface TollingService {

    //TODO use interceptor for authorization header, https://github.com/square/okhttp/wiki/Interceptors

    @GET("plazas/{position}")
    fun getNearPlazas(position : String): Deferred<List<TollingPlaza>>

    @POST("user/new")
    fun createUser(@Body user: User): Deferred<User>

    @GET("vehicle")
    fun getVehicleList(): Deferred<List<Vehicle>>

    @PUT("vehicle")
    fun addVehicle( @Body vehicle: Vehicle): Deferred<Vehicle>

    @GET("vehicle/{id}")
    fun getVehicleDetails(@Path("id") id: Int): Deferred<Vehicle>

    @GET("vehicle/{id}/transaction")
    fun getVehicleTransactions(@Path("id") id: Int): Deferred<List<TollingTransaction>>

    @GET("vehicle/{id}/transaction/open")
    fun getVehicleOpenTransactions(@Path("id") id: Int):Deferred<List<TollingTransaction>>

    @GET("vehicle/{id}/transaction/closed")
    fun getVehicleCLosedTransactions(@Path("id") id: Int): Deferred<List<TollingTransaction>>

    @GET("transaction")
    fun getTransactionList(@Query("owner") owner: String): Deferred<List<TollingTransaction>>

    @GET("transaction/closed")
    fun getClosedTransactionList(@Query("owner") owner: String): Deferred<List<TollingTransaction>>

    @GET("transaction/open")
    fun getOpenTransactionList(@Header("Authorization") authorization: String): Deferred<List<TollingTransaction>>

    @GET("transaction/{id}")
    fun getTransactionDetails( @Path("id") id: Int): Deferred<TollingTransaction>

    @POST("transaction/new")
    fun initiateTollingTransaction(@Body transaction: TollingTransaction): Deferred<TollingTransaction>

    @PUT("transaction/{id}/close")
    fun closeTollingTransaction( @Path("id") id: Int, @Body transaction: TollingTransaction): Deferred<TollingTransaction>



//    @POST("plaza/{id}/verify")
//    fun verifyPassage( @Path("id") id: Int,@Body vector: positionsVector): Deferred<Int> TODO this should gives us a percentage of certainty that person passed a certain plaza
//

}
