package pt.isel.ps.LI61N.g30.server.security

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.UserRepository
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingService
import pt.isel.ps.LI61N.g30.server.services.UserService
import java.util.*

@Component
class DomainUserDetailsService(
        private val userRepository: UserRepository,
        restTemplateBuilder: RestTemplateBuilder
) : UserDetailsService {

    private val log = LoggerFactory.getLogger(DomainUserDetailsService::class.java)
    val restTemplate: RestTemplate = restTemplateBuilder.build()
    val usersURI = "/users"

    lateinit var userService: UserService

    @Transactional
    override fun loadUserByUsername(login: String): UserDetails {
        log.info("Authenticating {}", login)
        val lowercaseLogin = login.toLowerCase(Locale.ENGLISH)
        val user: Optional<User> = userRepository.findByLogin(lowercaseLogin)
        if(!user.isPresent){
            val url = String.format("%s%s/%s", ClearingService.BASE_URI, usersURI, user.get().login)
            val result = try{
                restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, ClearingService.Companion.UserDTO::class.java)
            }catch (e: RestClientException){
                throw e
            }
            //if exists in clearing, create a new user here
            result.body?.let {
                val newUser = try {
                    userService.createUser(it)
                }catch (e: Throwable){
                    throw e
                }
                //created user sucessfully
                return userToCustomUserDetails(newUser)
            }

            //User does not exist in Clearing
            throw UsernameNotFoundException("User $lowercaseLogin was not found")
        }else{
            //User already exits
            return return userToCustomUserDetails(user.get())
        }
    }

    fun userToCustomUserDetails(user: User) =
        with(user) {
            CustomUserDetails(
                id,
                login,
                password_hash,
                roles.map { authority -> SimpleGrantedAuthority(authority.name) }
            )
        }
}