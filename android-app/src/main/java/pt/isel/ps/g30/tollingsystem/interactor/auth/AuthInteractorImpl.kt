package pt.isel.ps.g30.tollingsystem.interactor.auth

import android.util.Log
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor
import pt.isel.ps.g30.tollingsystem.data.api.model.User
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor


class AuthInteractorImpl(private val tollingService: TollingService, private val authInterceptor: HttpAuthInterceptor, private val syncrhonizationInteractor: SynchronizationInteractor ) : AuthInteractor {

    override suspend fun authenticate(login: String, password: String): Deferred<User> {
        val deferred = CompletableDeferred<User>()
        if (login.isNotEmpty() && password.isNotEmpty()) {

            authInterceptor.setCredentials(login, password)
            return deferred.apply {  complete(verifyAuthentication().await())}
        }

        deferred.completeExceptionally(Exception("failed Login"))
        return deferred
    }


    override suspend fun verifyAuthentication(): Deferred<User> {
        val deferred = CompletableDeferred<User>()
        val response = tollingService.authenticate().await()
        Log.v("AuthInteractor", "${response.code()}")
        if (response.isSuccessful && response.body()!= null) {
            syncrhonizationInteractor.VerifySynchronization()
            deferred.complete(response.body()!!)
            return deferred
        }

        deferred.completeExceptionally(Exception("failed Login"))
        return deferred
    }

    override fun logout() = authInterceptor.logout()
}