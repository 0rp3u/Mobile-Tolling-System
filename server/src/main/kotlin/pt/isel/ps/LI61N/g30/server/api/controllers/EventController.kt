package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import pt.isel.ps.LI61N.g30.server.model.domain.Event
import pt.isel.ps.LI61N.g30.server.api.input.InputEvent
import pt.isel.ps.LI61N.g30.server.api.input.InputTransaction
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.EventService
import pt.isel.ps.LI61N.g30.server.services.UserService
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RequestMapping("/events", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class EventController(
        val userService : UserService,
        val authService: AuthService,
        val eventService: EventService
) {

    val log = LoggerFactory.getLogger(EventController::class.java)

    //async
//    @Transactional
////    @RequestMapping(method = [RequestMethod.POST], value = "/begin")
////    fun BeginEvent(
////            @RequestBody input: InputEvent
////    ): DeferredResult<ResponseEntity<Event>> {
////        val userId = authService.authenticatedUser().id
////        val user = userService.getUserByid(userId)
////        log.info("Fetched user: ${user.login}")
////
////        val result = DeferredResult<ResponseEntity<Event>>(TimeUnit.SECONDS.toMillis(10))
////        CompletableFuture
////                .supplyAsync( { eventService.beginEvent(input.vehicle_id, input.toll, input.timestamp, input.geoLocations, user)} )
////                .thenAccept( { result.setResult(ResponseEntity.created(URI.create("/events/${it.uid.uid}/begin")).body(it))} )
////        return result
////    }

//    //sync
//    @Transactional
//    @RequestMapping(method = [RequestMethod.POST], value = "/begin")
//    fun BeginEvent(
//            @RequestBody input: InputEvent
//    ): ResponseEntity<Event> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        return eventService.beginEvent(input.vehicle_id, input.toll, input.timestamp, input.geoLocations, user).let {
//            with(it.event.first()){
//                ResponseEntity.created(URI.create("/events/${uid.uid}/begin")).body(this)
//            }
//        }
//    }

//    @Transactional
//    @RequestMapping(method = [RequestMethod.POST], value = "/{transaction_id}/end")
//    fun EndEvent(
//            @RequestBody input: InputEvent,
//            @PathVariable transaction_id: Long
//    ): DeferredResult<ResponseEntity<Void>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        val result = DeferredResult<ResponseEntity<Void>>(TimeUnit.SECONDS.toMillis(10))
//        CompletableFuture
//                .supplyAsync( { eventService.endEvent(input.vehicle_id, input.toll, input.timestamp, transaction_id, input.geoLocations, user)} )
//                .thenAccept( { result.setResult(ResponseEntity.noContent().build())} )
//        return result
//    }
//
//@Transactional
//@RequestMapping(method = [RequestMethod.POST], value = "/{transaction_id}/end")
//fun EndEvent(
//        @RequestBody input: InputEvent,
//        @PathVariable transaction_id: Long
//): ResponseEntity<Event> {
//    val userId = authService.authenticatedUser().id
//    val user = userService.getUserByid(userId)
//    log.info("Fetched user: ${user.login}")
//
//    return eventService.endEvent(transaction_id, input.toll, input.timestamp, input.geoLocations, user).let {
//        with(it.event.last()){
//            ResponseEntity.created(URI.create("/events/${uid.uid}/end")).body(this)
//        }
//    }
//}


//    @Transactional(readOnly = true)
//    @RequestMapping(method = [RequestMethod.GET])
//    fun getEvents(
//        //@RequestParam(name = "vehicle") vehicle_id: Long
//    ): DeferredResult<ResponseEntity<List<Event>>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        val result = DeferredResult<ResponseEntity<List<Event>>>(TimeUnit.SECONDS.toMillis(10))
//        CompletableFuture
//                .supplyAsync( { eventService.getAll(user)} )
//                .thenAccept( { result.setResult(ResponseEntity.ok(it))} )
//        return result
//    }

//    @Transactional(readOnly = true)
//    @RequestMapping(method = [RequestMethod.GET], value = "/status")
//    fun getLatestTransaction(
//            @RequestParam(name = "vehicle") vehicle_id: Long
//    ): DeferredResult<ResponseEntity<Transaction>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        val result = DeferredResult<ResponseEntity<Transaction>>(TimeUnit.SECONDS.toMillis(10))
//        CompletableFuture
//                .supplyAsync( { eventService.getLatestTransaction(vehicle_id, user)} )
//                .thenAccept( { result.setResult(ResponseEntity.ok(it))} )
//        return result
//    }

}