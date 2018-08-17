package pt.isel.ps.LI61N.g30.server.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.Ticket
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.RoleRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TicketRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.UserRepository
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingService


@Service
class UserService(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository,
        val ticketRepository: TicketRepository,
        val passwordEncoder: PasswordEncoder
) {


    fun createUser(user: ClearingService.Companion.UserDTO): User {
        with(user){
            if(userRepository.existsByLogin(nif.toString())) throw Exception("User already exists")

            val newUser = User(
                    login = nif.toString(),
                    password_hash = passwordEncoder.encode(password),
                    name = name,
                    roles = listOf(roleRepository.findById("USER").get()),
                    vehicles = mutableListOf(),
                    tickets = mutableListOf()
            )

            return userRepository.save(newUser)
        }
    }

    fun getAll(): List<User> =
            userRepository.findAll().toList()

    fun getUserByid(userID : Long): User =
            userRepository.findById(userID).get()

    fun getUserByLogin(login : String): User =
            userRepository.findByLogin(login).get()


    /** Tickets **/

    fun createTicket(user_id: Long, amount: Double, reason: String?, timestamp: java.util.Date): Ticket {
        val user = userRepository.findById(user_id).orElseThrow { Exception("Invalid user.") }
        return Ticket(user = user, amount = amount, reason = reason, timestamp = timestamp).also {
            user.tickets.add(it)
            userRepository.save(user)
        }
    }

    fun getTicket(ticket_id: Long, user: User): Ticket =
        ticketRepository.findById(ticket_id).orElseThrow { throw Exception("Invalid Ticket") }

    fun getTickets(user: User, page: Pageable): Page<Ticket> {
        return ticketRepository.findAll(page)
    }

}