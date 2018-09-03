package pt.isel.ps.g30.tollingsystem.interactor.auth

import android.util.Log
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser
import pt.isel.ps.g30.tollingsystem.data.database.model.User
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.user.UserInteractor


class AuthInteractorImpl(
        private val tollingService: TollingService,
        private val authInterceptor: HttpAuthInterceptor,
        private val syncrhonizationInteractor: SynchronizationInteractor,
        private val userInteractor: UserInteractor
) : AuthInteractor {

    override suspend fun authenticate(login: String, password: String): Deferred<User> {
        val deferred = CompletableDeferred<User>()
        if (login.isNotEmpty() && password.isNotEmpty()) {

            authInterceptor.setCredentials(login, password)
            val response = tollingService.authenticate().await()
            Log.v("AuthInteractor", "${response.code()}")
            if (response.isSuccessful && response.body()!= null) {
                userInteractor.setCurrentUser(response.body()!!)
                deferred.complete(verifyUserAuthenticationAndSyncronization().await())
                return deferred
            }
        }

        deferred.completeExceptionally(Exception("failed Login"))
        return deferred
    }

    override suspend fun verifyUserAuthenticationAndSyncronization(): Deferred<User> {

        val user = userInteractor.getCurrentUser().await()
        val deferred = CompletableDeferred<User>()
        if (user != null) {
            syncrhonizationInteractor.VerifySynchronization()
            deferred.complete(user)
            return deferred
        }

        deferred.completeExceptionally(Exception("No user Login"))
        return deferred
    }

    override fun logout() = authInterceptor.logout()
}