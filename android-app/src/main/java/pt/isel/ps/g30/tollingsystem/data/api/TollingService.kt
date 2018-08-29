package pt.isel.ps.g30.tollingsystem.data.api

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.model.*

import retrofit2.Response
import retrofit2.http.*

interface TollingService {

    @GET("users/authentication")
    fun authenticate(): Deferred<Response<User>>

    @GET("plazas/{position}")
    fun getNearPlazas(position : LatLong): Deferred<List<TollingPlaza>>

    @GET("plazas")
    fun getAllPlazas(): Deferred<List<TollingPlaza>>

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

    @POST("transaction/verify")
    fun verifyTollingTransaction(@Body passageInfo: TollPassageInfo): Deferred<Boolean>

    @POST("transaction/new")
    fun initiateTollingTransaction(@Body transaction: TollingTransaction): Deferred<TollingTransaction>

    @POST("transaction/close")
    fun closeTollingTransaction( @Body transaction: TollingTransaction): Deferred<TollingTransaction>

    @POST("transaction/cancel")
    fun cancelTollingTransaction( @Body transaction: TollingTransaction): Deferred<TollingTransaction>



}
