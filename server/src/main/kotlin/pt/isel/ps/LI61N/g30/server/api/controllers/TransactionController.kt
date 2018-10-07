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
import pt.isel.ps.LI61N.g30.server.api.output.OutputTransaction
import pt.isel.ps.LI61N.g30.server.api.output.createOutputTransaction
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

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "/{id}")
    fun getOneTransaction(
            @PathVariable transaction_id: Long
    ): ResponseEntity<OutputTransaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.getTransaction(transaction_id, user)
                .let { createOutputTransaction(it) })
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "")
    fun getTransactions(
            @RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss.SSS") date: Date?
    ): ResponseEntity<List<OutputTransaction>> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.getTransactions(date, user).map { createOutputTransaction(it) })
    }

//    //TODO with DeferredResult
//    @Transactional(readOnly = true)
//    @RequestMapping(method = [RequestMethod.GET], value = "/status")
//    fun getLatestTransactionFromVehicle(
//            @RequestParam(name = "vehicle") vehicle_id: Long
//    ): ResponseEntity<Transaction> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        return ResponseEntity.ok(transactionService.getLatestTransaction(vehicle_id, user))
//    }

    @Transactional
    @RequestMapping(method = [RequestMethod.PUT], value = "/{transaction_id}/confirm")
    fun confirmTransaction(
            @PathVariable transaction_id: Long,
            @RequestBody input: InputTransaction
    ): ResponseEntity<OutputTransaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.confirmTransaction(transaction_id, input, user))
    }

    @RequestMapping(method = [RequestMethod.PUT], value = "/{transaction_id}/cancel")
    fun cancelTransaction(
            @PathVariable transaction_id: Long
    ): ResponseEntity<OutputTransaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.cancelTransaction(transaction_id, user))
    }

    @RequestMapping(method = [RequestMethod.POST], value = "/create")
    fun createTransaction(
        @RequestBody inputTransaction: InputTransaction
    ): ResponseEntity<OutputTransaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(transactionService.create(inputTransaction, user))
    }

    @RequestMapping(method = [RequestMethod.POST], value = "/{transaction_id}/close")
    fun confirmTransaction(
            @PathVariable transaction_id: Long
    ): ResponseEntity<OutputTransaction> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(createOutputTransaction(transactionService.close(transaction_id)))
    }



//    @RequestMapping(method = [RequestMethod.POST], value = "/{id}/confirmation")
////    fun confirmTransaction(): ResponseEntity<List<Transaction>> {
////        val userId = authService.authenticatedUser().id
////        val user = userService.getUserByid(userId)
////        log.info("Fetched user: ${user.login}")
////
////        return ResponseEntity.ok(transactionService.getTransactions(user))
////    }

}




