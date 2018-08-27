package pt.isel.ps.LI61N.g30.server.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUserDetails(
        val id: Long,
        login:String,
        password_hash : String,
        roles: Collection<GrantedAuthority>
) : User(login, password_hash, roles)