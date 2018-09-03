package pt.isel.ps.g30.tollingsystem.interactor.auth

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser
import pt.isel.ps.g30.tollingsystem.data.database.model.User
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface AuthInteractor : BaseInteractor {

    suspend fun authenticate(login:String,password:String) : Deferred<User>

    suspend fun verifyUserAuthenticationAndSyncronization(): Deferred<User>


    fun logout()

}