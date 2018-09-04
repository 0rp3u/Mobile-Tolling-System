package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.api.input.InputAmendTransaction
import pt.isel.ps.LI61N.g30.server.api.input.InputTransaction
import pt.isel.ps.LI61N.g30.server.model.domain.Event
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.EventService
import pt.isel.ps.LI61N.g30.server.services.TransactionService
import pt.isel.ps.LI61N.g30.server.services.UserService
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.net.URI
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RequestMapping("/transactions", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class TransactionController(
        val userService : UserService,
        val authService: AuthService,
        val transactionService: TransactionService
) {

    val log = LoggerFactory.getLogger(TransactionController::class.java)

    //TODO with DeferredResult
    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "/status")
    fun getLatestTransactionFromVehicle(
            @RequestParam(name = "vehicle") vehicle_id: Long
    ): ResponseEntity<Transaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.getLatestTransaction(vehicle_id, user))
    }

    @Transactional
    @RequestMapping(method = [RequestMethod.PUT], value = "/{transaction_id}/confirm")
    fun amendTransaction(
            @PathVariable transaction_id: Long,
            @RequestBody input: InputAmendTransaction
    ): ResponseEntity<Void> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        transactionService.confirmTransaction(transaction_id, input.new_begin_toll, input.new_end_toll, user)
        return ResponseEntity.noContent().build()
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "")
    fun getTransactions(
            @RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss.SSS") date: Date?
    ): ResponseEntity<List<Transaction>> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.getTransactions(date, user))
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "/{id}")
    fun getOneTransaction(
            @PathVariable transaction_id: Long
    ): ResponseEntity<Transaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.getTransaction(transaction_id, user))
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.PUT], value = "/{transaction_id}/cancel")
    fun cancelTransaction(
            @PathVariable transaction_id: Long
    ): ResponseEntity<Transaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.cancelTransaction(transaction_id, user))
    }

    //TODO
    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.POST], value = "/create")
    fun createTransaction(

    ): ResponseEntity<Transaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.create())
    }

//    @Transactional(readOnly = true)
//    @RequestMapping(method = [RequestMethod.POST], value = "/{id}/confirmation")
//    fun confirmTransaction(): ResponseEntity<List<Transaction>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        return ResponseEntity.ok(transactionService.getTransactions(user))
//    }

}




