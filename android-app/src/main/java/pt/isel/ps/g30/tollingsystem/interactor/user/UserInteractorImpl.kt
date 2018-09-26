package pt.isel.ps.g30.tollingsystem.interactor.user

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.experimental.*
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.User
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser

class UserInteractorImpl(
        private val tollingSystemDatabase: TollingSystemDatabase
) : UserInteractor {


    override suspend fun getCurrentUser() =async { tollingSystemDatabase.UserDao().findCurrent() }

    override suspend fun setCurrentUser(apiUser: ApiUser) = async{
        val user = tollingSystemDatabase.UserDao().findById(apiUser.id) ?: User(apiUser.id, apiUser.name, apiUser.login).also { tollingSystemDatabase.UserDao().insert(it) }
        user.current = true
        tollingSystemDatabase.UserDao().update(user)

        return@async user
    }

    override suspend fun removeCurrentUser() {
        getCurrentUser().await()?.let {
            it.current = false
            tollingSystemDatabase.UserDao().update(it)
        }
    }
}
