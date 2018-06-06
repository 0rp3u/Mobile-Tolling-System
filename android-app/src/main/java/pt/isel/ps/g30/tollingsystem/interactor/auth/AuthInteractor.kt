package pt.isel.ps.g30.tollingsystem.interactor.auth

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface AuthInteractor : BaseInteractor {

    suspend fun authenticate(login:String,password:String) : Deferred<Boolean> //TODO not boolean, to discuss


    suspend fun verifyToken() : Deferred<Boolean>

}