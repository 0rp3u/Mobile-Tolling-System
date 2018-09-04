package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.Event
import pt.isel.ps.LI61N.g30.server.model.domain.Toll
import java.util.*

@Repository
interface TollRepository : CrudRepository<Toll, Long> {

    fun findByUpdatedAfter(date: Date): List<Toll>
}