package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.Event
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import java.util.*

@Repository
interface EventRepository : PagingAndSortingRepository<Event, Long> {

    fun findByTimestampAfter(date: Date): List<Event>

    fun findByOrderByTimestampDesc(pageable: Pageable = Pageable.unpaged()): List<Event>

    fun findOneByOrderByTimestampDesc(pageable: Pageable = Pageable.unpaged()): List<Event>
}