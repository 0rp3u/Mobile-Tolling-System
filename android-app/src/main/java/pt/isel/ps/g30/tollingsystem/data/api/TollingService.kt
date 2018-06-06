package pt.isel.ps.g30.tollingsystem.data.api

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.model.*

import retrofit2.Response
import retrofit2.http.*

interface TollingService {

    @GET("/users/authentication")
    fun authenticate(): Deferred<Response<Void>>

    @GET("plazas/{position}")
    fun getNearPlazas(position : String): Deferred<List<TollingPlaza>>

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
    fun getOpenTransactionList(): Deferred<List<TollingTransaction>>

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