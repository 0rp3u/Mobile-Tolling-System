package pt.isel.ps.LI61N.g30.server.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.security.CustomUserDetails

@Service
object AuthService {

    fun authenticatedUser(): CustomUserDetails =
            SecurityContextHolder.getContext().authentication.principal as CustomUserDetails

    val authenticatedUserById: Long
        get() = authenticatedUser().id
}