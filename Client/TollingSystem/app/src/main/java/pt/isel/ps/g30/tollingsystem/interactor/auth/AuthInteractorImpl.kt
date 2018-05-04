package pt.isel.ps.g30.tollingsystem.interactor.auth

import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import okhttp3.Response
import pt.isel.ps.g30.tollingsystem.api.TollingService
import pt.isel.ps.g30.tollingsystem.interceptor.HttpAuthInterceptor


class AuthInteractorImpl(private val tollingService: TollingService, private val authInterceptor: HttpAuthInterceptor) : AuthInteractor {

    override suspend fun authenticate(login: String, password: String): Deferred<Boolean> {
        val deferred = CompletableDeferred<Boolean>()

        deferred.complete(true) //TODO will be a call to API, failure should complete the deferred with AuthException, deferred on TollingService should be of type Response ?

        authInterceptor.setCredentials(login, password)

        return deferred
    }
}