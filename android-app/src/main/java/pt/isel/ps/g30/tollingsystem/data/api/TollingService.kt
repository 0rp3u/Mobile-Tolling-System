package pt.isel.ps.g30.tollingsystem.data.api

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.model.*

import retrofit2.Response
import retrofit2.http.*

interface TollingService {

    @GET("users/authentication/")
    fun authenticate(): Deferred<Response<User>>

    @GET("tolls/{geoLocation}")
    fun getNearPlazas(@Path("geoLocation") position : LatLong): Deferred<List<TollingPlaza>>

    @GET("tolls")
    fun getAllPlazas(@Query("date") date:String? = null): Deferred<List<TollingPlaza>>

    @GET("vehicles")
    fun getVehicleList(@Query("date") date:String? = null): Deferred<List<Vehicle>>

    @POST("tolls/{id}/verify")
    fun verifyTollPassage(@Path("id") id: Int, @Body passageInfo: List<Point>): Deferred<Float>

    @POST("transactions/create")
    fun createTollingTransaction(@Body transaction: TransactionInfo): Deferred<TollingTransaction>

    @PUT("transactions/{id}/cancel")
    fun cancelTollingTransaction( @Path("id") id: Int): Deferred<TollingTransaction>

    @PUT("transactions/{id}/confirm")
    fun confirmTollingTransaction(@Path("id") id: Int, @Body transaction: TollingTransaction): Deferred<TollingTransaction>

    @PUT("vehicles")
    fun addVehicle( @Body vehicle: Vehicle): Deferred<Vehicle>

    @GET("vehicles/{id}")
    fun getVehicleDetails(@Path("id") id: Int): Deferred<Vehicle>

    @GET("vehicles/{id}/transaction")
    fun getVehicleTransactions(@Path("id") id: Int,@Query("date") date:String? = null): Deferred<List<TollingTransaction>>

    @GET("transactions")
    fun getTransactionList(@Query("date") date:String? = null): Deferred<List<TollingTransaction>>

    @GET("transactions/closed")
    fun getClosedTransactionList(@Query("date") date:String? = null): Deferred<List<TollingTransaction>>

    @GET("transactions/open")
    fun getOpenTransactionList(@Query("date") date:String? = null): Deferred<List<TollingTransaction>>


}
