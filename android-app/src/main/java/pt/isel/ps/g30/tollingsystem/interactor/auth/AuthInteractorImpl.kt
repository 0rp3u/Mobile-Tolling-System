package pt.isel.ps.g30.tollingsystem.interactor.auth

import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.delay
import okhttp3.Response
import pt.isel.ps.g30.tollingsystem.api.TollingService
import pt.isel.ps.g30.tollingsystem.interceptor.HttpAuthInterceptor


class AuthInteractorImpl(private val tollingService: TollingService, private val authInterceptor: HttpAuthInterceptor) : AuthInteractor {

    override suspend fun authenticate(login: String, password: String): Deferred<Boolean> {
        val deferred = CompletableDeferred<Boolean>()

        delay(1000)
        if((0..2).shuffled().last() >1){
            deferred.complete(true) //TODO will be a call to API, failure should complete the deferred with AuthException, deferred on TollingService should be of type Response ?

        }
        deferred.completeExceptionally(Exception("failed Login"))
        authInterceptor.setCredentials(login, password)

        return deferred
    }
}