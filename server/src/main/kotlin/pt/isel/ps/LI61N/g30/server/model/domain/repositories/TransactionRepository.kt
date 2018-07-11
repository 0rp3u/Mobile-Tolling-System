package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import java.util.*

@Repository
interface TransactionRepository : PagingAndSortingRepository<Transaction, Long> {

    fun findByTimestampAfter(date: Date): List<Transaction>

    fun findByOrderByTimestampDesc(pageable: Pageable = Pageable.unpaged()): List<Transaction>

    fun findOneByOrderByTimestampDesc(pageable: Pageable = Pageable.unpaged()): List<Transaction>
}