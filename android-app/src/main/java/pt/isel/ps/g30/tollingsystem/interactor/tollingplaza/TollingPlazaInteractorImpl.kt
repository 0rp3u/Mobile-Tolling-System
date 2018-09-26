package pt.isel.ps.g30.tollingsystem.interactor.tollingplaza
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pt.isel.ps.g30.tollingsystem.data.api.TollingService
import pt.isel.ps.g30.tollingsystem.data.api.model.Point
import pt.isel.ps.g30.tollingsystem.data.database.TollingSystemDatabase
import pt.isel.ps.g30.tollingsystem.data.database.model.TollingPlaza

class TollingPlazaInteractorImpl(private val tollingSystemDatabase: TollingSystemDatabase, private val tollingService: TollingService) : TollingPlazaInteractor {

    override  suspend fun getTollPlazaList() : Deferred<List<TollingPlaza>>{
        return async { tollingSystemDatabase.TollingDao().findActive() }
    }

    override  suspend fun getActiveTollPlazaList() : Deferred<List<TollingPlaza>>{

        return async { tollingSystemDatabase.TollingDao().findActive() }
    }

    override suspend fun getTollPlaza(id: Int): Deferred<TollingPlaza> {

        return async { tollingSystemDatabase.TollingDao().findById(id) }
    }

    override suspend fun verifyPassage(plazaId: Int, points: List<Point>): Deferred<Float>
            = tollingService.verifyTollPassage(plazaId, points)

}
