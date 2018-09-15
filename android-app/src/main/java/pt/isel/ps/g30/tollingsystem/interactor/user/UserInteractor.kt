package pt.isel.ps.g30.tollingsystem.interactor.user

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor
import pt.isel.ps.g30.tollingsystem.data.database.model.User
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser

interface UserInteractor : BaseInteractor {

    suspend fun getCurrentUser() : Deferred<User?>

    suspend fun setCurrentUser(apiUser: ApiUser) : Deferred<User>

    suspend fun removeCurrentUser()

}
