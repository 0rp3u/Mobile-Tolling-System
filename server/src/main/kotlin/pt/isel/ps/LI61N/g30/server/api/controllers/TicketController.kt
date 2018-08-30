package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import pt.isel.ps.LI61N.g30.server.api.input.InputTicket
import pt.isel.ps.LI61N.g30.server.model.domain.Ticket
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.TransactionService
import pt.isel.ps.LI61N.g30.server.services.UserService
import java.net.URI

@RequestMapping("/tickets", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class TicketController(
        val userService : UserService,
        val authService: AuthService,
        val transactionService: TransactionService
) {

    val log = LoggerFactory.getLogger(TicketController::class.java)

    //TODO ADMIN
    @Transactional
    @RequestMapping(method = [RequestMethod.POST], value = "")
    fun createTicket(
            @RequestBody input: InputTicket
    ): ResponseEntity<Ticket> {
        with(input){
            val ticket = userService.createTicket(user_id, amount, reason, timestamp)
            return ResponseEntity
                    .created(URI.create("users/tickets/{ticket_id}"))
                    .body(ticket)
        }
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "")
    fun getTickets(
            @PathVariable ticket_id: Long,
            page: Pageable
    ): ResponseEntity<Page<Ticket>>{
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(userService.getTickets(user, page))
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "/{ticket_id}")
    fun getOneTicket(
            @PathVariable ticket_id: Long
    ): ResponseEntity<Ticket>{
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(userService.getTicket(ticket_id, user))
    }

}