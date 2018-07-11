package pt.isel.ps.LI61N.g30.server.model.domain.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.ps.LI61N.g30.server.model.domain.Role

@Repository
interface RoleRepository : CrudRepository<Role, String>