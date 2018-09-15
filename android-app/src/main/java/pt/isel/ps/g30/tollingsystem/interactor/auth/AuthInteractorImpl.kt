package pt.isel.ps.g30.tollingsystem.interactor.auth

import android.content.Intent
import android.util.Log
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch
import pt.isel.ps.g30.tollingsystem.TollingSystemApp
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.interceptor.HttpAuthInterceptor
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser
import pt.isel.ps.g30.tollingsystem.data.database.model.User
import pt.isel.ps.g30.tollingsystem.interactor.syncronization.SynchronizationInteractor
import pt.isel.ps.g30.tollingsystem.interactor.user.UserInteractor
import pt.isel.ps.g30.tollingsystem.view.login.LoginActivity


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
            if (response.isSuccessful && response.body() != null) {
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

    override fun logout() = launch {
        authInterceptor.logout()
        userInteractor.removeCurrentUser()
        TollingSystemApp.instance.startActivity(
                Intent(TollingSystemApp.instance, LoginActivity::class.java)
                        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK; }
        )
    }
}