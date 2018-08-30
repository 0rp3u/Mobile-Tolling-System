package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.TransactionAmendment
import java.util.*

@Repository
interface TransactionAmendmentRepository : PagingAndSortingRepository<TransactionAmendment, Long>