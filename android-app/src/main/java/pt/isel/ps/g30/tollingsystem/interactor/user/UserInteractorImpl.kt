package pt.isel.ps.g30.tollingsystem.interactor.user

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.experimental.*
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.User
import pt.isel.ps.g30.tollingsystem.data.api.model.User as ApiUser

class UserInteractorImpl(
        private val tollingSystemDatabase: TollingSystemDatabase,
        private val sharedPreferences: SharedPreferences
) : UserInteractor {


    companion object {
        const val USER_KEY_ID = "USER_KEY_ID"
    }

    override suspend fun getCurrentUser() : Deferred<User?>{
        return async { tollingSystemDatabase.UserDao().findById(sharedPreferences.getInt(USER_KEY_ID, -1))}
    }

    override fun setCurrentUser(apiUser: ApiUser) = async{
        val user = tollingSystemDatabase.UserDao().findById(apiUser.id) ?: User(apiUser.id, apiUser.name, apiUser.login).also { tollingSystemDatabase.UserDao().insert(it) }
        sharedPreferences.edit {
            putInt(USER_KEY_ID, user.id)
        }


        return@async user
    }
}
