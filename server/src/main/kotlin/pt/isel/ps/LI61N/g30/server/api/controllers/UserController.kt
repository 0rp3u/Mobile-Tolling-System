package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.UserService

@RequestMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class UserController(
        val authService: AuthService,
        val userService: UserService,
        restTemplateBuilder: RestTemplateBuilder
) {

    val log = LoggerFactory.getLogger(UserController::class.java)

//    //TODO ADMIN
//    @Transactional
//    @RequestMapping(method = [RequestMethod.POST], value = "/tickets")
//    fun createTicket(
//            @RequestBody input: InputTicket
//    ): ResponseEntity<Ticket> {
//        with(input){
//            val ticket = userService.createTicket(user_id, amount, reason, timestamp)
//            return ResponseEntity
//                    .created(URI.create("users/tickets/{ticket_id}"))
//                    .body(ticket)
//        }
//    }
//
//    @Transactional
//    @RequestMapping(method = [RequestMethod.GET], value = "/tickets")
//    fun getTickets(
//        @PathVariable ticket_id: Long,
//        page: Pageable
//    ): ResponseEntity<Page<Ticket>>{
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        return ResponseEntity.ok(userService.getTickets(user, page))
//    }
//
//    @Transactional
//    @RequestMapping(method = [RequestMethod.GET], value = "/tickets/{ticket_id}")
//    fun getOneTicket(
//            @PathVariable ticket_id: Long
//    ): ResponseEntity<Ticket>{
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        return ResponseEntity.ok(userService.getTicket(ticket_id, user))
//    }


//    val restTemplate: RestTemplate = restTemplateBuilder.build()
//    val usersURI = "/users"
//
//    @Transactional(readOnly = true)
////    @RequestMapping(method = [RequestMethod.GET], value="/{id}")
////    fun findOne(page: Pageable): ResponseEntity<JSONPObject> {
////        val users = userService.getUserByLogin()
////
////        return ResponseEntity.ok(JSONPObject("", ""))
////    }

    @Transactional
    @GetMapping("/authentication")
    fun authentication(): ResponseEntity<User> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)

        return ResponseEntity.ok(user)
    }

}