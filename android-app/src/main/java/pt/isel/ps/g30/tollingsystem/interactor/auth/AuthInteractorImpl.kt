package pt.isel.ps.g30.tollingsystem.interactor.auth

import android.util.Log
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.delay
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.interceptor.HttpAuthInterceptor


class AuthInteractorImpl(private val tollingService: TollingService, private val authInterceptor: HttpAuthInterceptor) : AuthInteractor {

    override suspend fun authenticate(login: String, password: String): Deferred<Boolean> {
        val deferred = CompletableDeferred<Boolean>()
        deferred.complete(true)
        return deferred

        if(login.isNotEmpty() && password.isNotEmpty()) {

            authInterceptor.setCredentials(login, password)
            val response = tollingService.authenticate().await()
            Log.v("AUTHInteractor", "${response.code()}")
            if (response.isSuccessful) {
                deferred.complete(true)
                return deferred
            }
        }

        deferred.completeExceptionally(Exception("failed Login"))

        return deferred
    }

    override suspend fun verifyToken(): Deferred<Boolean> {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return CompletableDeferred<Boolean>().also { it.complete(true) }
    }
}