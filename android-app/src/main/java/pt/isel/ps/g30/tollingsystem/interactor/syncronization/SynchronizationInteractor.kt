package pt.isel.ps.g30.tollingsystem.interactor.syncronization


import pt.isel.ps.g30.tollingsystem.data.api.model.User
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface SynchronizationInteractor : BaseInteractor {

    suspend fun SynchronizeUserData(apiUser: User)

    suspend fun VerifySynchronization()

}
