package pt.isel.ps.LI61N.g30.server.services.clearing

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.UserRepository
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.RoleRepository


@Service
class ClearingUserService(
        val userRepository: UserRepository,
        val roleRepository: RoleRepository,
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

}