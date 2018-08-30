package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.Ticket
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import java.util.*

@Repository
interface TicketRepository : PagingAndSortingRepository<Ticket, Long> {

}