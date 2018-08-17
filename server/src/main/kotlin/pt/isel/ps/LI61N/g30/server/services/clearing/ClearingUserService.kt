package pt.isel.ps.LI61N.g30.server.services.clearing

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.UserRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.RoleRepository


@Service
class UserService(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository,
        val passwordEncoder: PasswordEncoder
) {


}