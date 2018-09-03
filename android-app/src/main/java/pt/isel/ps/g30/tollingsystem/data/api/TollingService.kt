package pt.isel.ps.g30.tollingsystem.data.api

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.model.*
import pt.isel.ps.g30.tollingsystem.data.database.model.TemporaryTransaction

import retrofit2.Response
import retrofit2.http.*

interface TollingService {

    @GET("users/authentication")
    fun authenticate(): Deferred<Response<User>>

    @GET("tolls/{position}")
    fun getNearPlazas(position : LatLong): Deferred<List<TollingPlaza>>

    @GET("tolls")
    fun getAllPlazas(): Deferred<List<TollingPlaza>>

    @GET("vehicles")
    fun getVehicleList(): Deferred<List<Vehicle>>

    @PUT("vehicles")
    fun addVehicle( @Body vehicle: Vehicle): Deferred<Vehicle>

    @GET("vehicles/{id}")
    fun getVehicleDetails(@Path("id") id: Int): Deferred<Vehicle>

    @GET("vehicles/{id}/transaction")
    fun getVehicleTransactions(@Path("id") id: Int): Deferred<List<TollingTransaction>>

    @GET("transaction")
    fun getTransactionList(): Deferred<List<TollingTransaction>>

    @GET("transaction/closed")
    fun getClosedTransactionList(): Deferred<List<TollingTransaction>>

    @GET("transaction/open")
    fun getOpenTransactionList(): Deferred<List<TollingTransaction>>

    @POST("transaction/verify")
    fun verifyTollPassage(@Body passageInfo: TollPassageInfo): Deferred<Boolean>

    @POST("transaction/create")
    fun createTollingTransaction(@Body transaction: TransactionInfo): Deferred<TollingTransaction>

    @PUT("transaction/{id}/confirm")
    fun confirmTollingTransaction(@Path("id") id: Int, @Body transaction: TollingTransaction): Deferred<TollingTransaction>

    @PUT("transaction/{id}/cancel")
    fun cancelTollingTransaction( @Path("id") id: Int): Deferred<TollingTransaction>



}
