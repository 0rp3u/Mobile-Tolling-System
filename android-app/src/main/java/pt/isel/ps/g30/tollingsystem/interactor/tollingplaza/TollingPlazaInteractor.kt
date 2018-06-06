package pt.isel.ps.g30.tollingsystem.interactor.tollingplaza

import kotlinx.coroutines.experimental.Deferred
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza
import pt.isel.ps.g30.tollingsystem.interactor.BaseInteractor

interface TollingPlazaInteractor : BaseInteractor {

    suspend fun getTollPlazaList() : Deferred<List<TollingPlaza>>

    suspend fun getActiveTollPlazaList() : Deferred<List<TollingPlaza>>

    suspend fun getTollPlaza(id: Int) : Deferred<TollingPlaza>

}
