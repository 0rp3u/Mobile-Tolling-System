package pt.isel.ps.g30.tollingsystem.interactor.auth

import android.util.Log
import androidx.core.content.edit
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor


class AuthInteractorImpl(private val tollingService: TollingService, private val authInterceptor: HttpAuthInterceptor) : AuthInteractor {

    override suspend fun authenticate(login: String, password: String): Deferred<Boolean> {
        val deferred = CompletableDeferred<Boolean>()
        if (login.isNotEmpty() && password.isNotEmpty()) {

            authInterceptor.setCredentials(login, password)
            return deferred.apply {  complete(verifyToken().await())}
        }

        deferred.completeExceptionally(Exception("failed Login"))
        return deferred
    }


    override suspend fun verifyToken(): Deferred<Boolean> {
        val deferred = CompletableDeferred<Boolean>()
        val response = tollingService.authenticate().await()
        Log.v("AuthInteractor", "${response.code()}")
        if (response.isSuccessful) {
            deferred.complete(true)
            return deferred
        }

        deferred.completeExceptionally(Exception("failed Login"))
        return deferred
    }

    override fun logout() = authInterceptor.logout()
}