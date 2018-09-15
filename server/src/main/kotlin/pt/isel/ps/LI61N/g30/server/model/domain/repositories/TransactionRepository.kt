package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.*
import java.util.*

@Repository
interface TransactionRepository : PagingAndSortingRepository<Transaction, Long> {

    fun findTop1ByVehicleOrderByCreatedDesc(vehicle: Vehicle): Optional<Transaction>

    fun findByUpdatedAfter(date: Date): List<Transaction>
}