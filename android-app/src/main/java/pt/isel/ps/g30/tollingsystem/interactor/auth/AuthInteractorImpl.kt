package pt.isel.ps.g30.tollingsystem.interactor.auth

import android.util.Log
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor


class AuthInteractorImpl(private val tollingService: TollingService, private val authInterceptor: HttpAuthInterceptor) : AuthInteractor {

    override suspend fun authenticate(login: String, password: String): Deferred<Int> {
        val deferred = CompletableDeferred<Int>()
        if (login.isNotEmpty() && password.isNotEmpty()) {

            authInterceptor.setCredentials(login, password)
            return deferred.apply {  complete(verifyAuthentication().await())}
        }

        deferred.completeExceptionally(Exception("failed Login"))
        return deferred
    }


    override suspend fun verifyAuthentication(): Deferred<Int> {
        val deferred = CompletableDeferred<Int>()
        val response = tollingService.authenticate().await()
        Log.v("AuthInteractor", "${response.code()}")
        if (response.isSuccessful) {
            deferred.complete()
            return deferred
        }

        deferred.completeExceptionally(Exception("failed Login"))
        return deferred
    }

    override fun logout() = authInterceptor.logout()
}