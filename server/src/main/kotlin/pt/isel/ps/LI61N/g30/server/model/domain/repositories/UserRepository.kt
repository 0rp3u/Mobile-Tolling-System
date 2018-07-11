package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.User
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun existsByLogin(login: String): Boolean

    fun findByLogin(login: String): Optional<User>

}