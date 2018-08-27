package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.TripAmendment
import java.util.*

@Repository
interface TripAmendmentRepository : PagingAndSortingRepository<TripAmendment, Long>