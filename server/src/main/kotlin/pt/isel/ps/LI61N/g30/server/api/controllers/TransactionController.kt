package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import pt.isel.ps.LI61N.g30.server.model.domain.Transaction
import pt.isel.ps.LI61N.g30.server.api.controllers.input.InputTransaction
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.TransactionService
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingUserService
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RequestMapping("/transactions", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class TransactionController(
        val userService : ClearingUserService,
        val authService: AuthService,
        val transactionService: TransactionService
) {

    val log = LoggerFactory.getLogger(TransactionController::class.java)

    @Transactional
    @RequestMapping(method = [RequestMethod.POST], value = "/begin")
    fun BeginTransaction(
            @RequestBody input: InputTransaction
    ): DeferredResult<ResponseEntity<Transaction>> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        val result = DeferredResult<ResponseEntity<Transaction>>(TimeUnit.SECONDS.toMillis(10))
        CompletableFuture
                .supplyAsync( { transactionService.beginTransaction(input.vehicle_id, input.toll, input.timestamp, input?.geoLocations, user)} )
                .thenAccept( { result.setResult(ResponseEntity.created(URI.create("/transactions/${it.uid.uid}/begin")).body(it))} )
        return result
    }

    @Transactional
    @RequestMapping(method = [RequestMethod.POST], value = "/{trip_id}/end")
    fun EndTransaction(
            @RequestBody input: InputTransaction,
            @PathVariable trip_id: Long
    ): DeferredResult<ResponseEntity<Void>> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        val result = DeferredResult<ResponseEntity<Void>>(TimeUnit.SECONDS.toMillis(10))
        CompletableFuture
                .supplyAsync( { transactionService.registerTrip(input)} )
                .thenAccept( { result.setResult(ResponseEntity.noContent().build())} )
        return result
    }

//    @Transactional(readOnly = true)
//    @RequestMapping(method = [RequestMethod.GET])
//    fun getTransactions(
//        //@RequestParam(name = "vehicle") vehicle_id: Long
//    ): DeferredResult<ResponseEntity<List<Transaction>>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        val result = DeferredResult<ResponseEntity<List<Transaction>>>(TimeUnit.SECONDS.toMillis(10))
//        CompletableFuture
//                .supplyAsync( { transactionService.getAll(user)} )
//                .thenAccept( { result.setResult(ResponseEntity.ok(it))} )
//        return result
//    }

//    @Transactional(readOnly = true)
//    @RequestMapping(method = [RequestMethod.GET], value = "/status")
//    fun getLatestTrip(
//            @RequestParam(name = "vehicle") vehicle_id: Long
//    ): DeferredResult<ResponseEntity<Trip>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        val result = DeferredResult<ResponseEntity<Trip>>(TimeUnit.SECONDS.toMillis(10))
//        CompletableFuture
//                .supplyAsync( { transactionService.getLatestTrip(vehicle_id, user)} )
//                .thenAccept( { result.setResult(ResponseEntity.ok(it))} )
//        return result
//    }

}