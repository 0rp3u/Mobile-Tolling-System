package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.Event
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import java.util.*

@Repository
interface VehicleRepository : CrudRepository<Vehicle, Long> {

   fun findByOwner(user: User): List<Vehicle>
   fun findByOwnerAndId(user: User, id: Long): Optional<Vehicle>

    @Query("select u from Vehicle u where u.updated > ?1 and u.owner = ?2")
   fun findByUpdatedAfterAndOwner(date: Date, user: User): List<Vehicle>
}