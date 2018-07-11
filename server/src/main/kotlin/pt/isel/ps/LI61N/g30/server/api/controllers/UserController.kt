package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import pt.isel.ps.LI61N.g30.server.api.controllers.input.Login
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.UserRepository
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingUserService

@RequestMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class UserController(
        val userService : ClearingUserService,
        val authService: AuthService,
        val userRepository: UserRepository,
        restTemplateBuilder: RestTemplateBuilder
) {

    val log = LoggerFactory.getLogger(UserController::class.java)
    val restTemplate: RestTemplate = restTemplateBuilder.build()
    val usersURI = "/users"

//    @Transactional(readOnly = true)
////    @RequestMapping(method = [RequestMethod.GET], value="/{id}")
////    fun findOne(page: Pageable): ResponseEntity<JSONPObject> {
////        val users = userService.getUserByLogin()
////
////        return ResponseEntity.ok(JSONPObject("", ""))
////    }

//    @Transactional
//    @RequestMapping(method = [RequestMethod.POST], value = "/authentication")
//    fun authentication(
//            @RequestBody input: Login
//    ): ResponseEntity<User> {
//        //check if user already exists in repository
//        val user = userRepository.findByLogin(input.username)
//        //if not, check if it exists in clearing server
//        if(!user.isPresent){
//            val url = String.format("%s%s/%s", ClearingService.BASE_URI, usersURI, input.username)
//            val result = try{
//                restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, ClearingService.Companion.UserDTO::class.java)
//            }catch (e: RestClientException){
//                throw e
//            }
//            //if exists in clearing, create a new user here
//            result.body?.let {
//                val newUser = try {
//                    userService.createUser(it)
//                }catch (e: Throwable){
//                    throw e
//                }
//                return ResponseEntity.created(URI.create("/users/${newUser.login}")).body(newUser)
//            }
//            //TODO Status Code???
//            return ResponseEntity.unprocessableEntity().build()
//        }else{
//            //User already exits
//            return ResponseEntity.ok(user.get())
//        }
//    }

    @GetMapping(value = "/self")
    fun test(): ResponseEntity<User> {
        log.info("routing: /test")
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Authenticated User: $userId")
        log.info("Username: ${user.login}")
        return ResponseEntity.ok(user)
    }

    @GetMapping(value = "/admin")
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAll()
        return ResponseEntity.ok(users)
    }


    @Transactional
    @RequestMapping(method = [RequestMethod.POST], value = "/ticket")
    fun createTicket(
            @RequestBody input: Login
    ): ResponseEntity<Void> {

        return ResponseEntity.noContent().build()
    }

}