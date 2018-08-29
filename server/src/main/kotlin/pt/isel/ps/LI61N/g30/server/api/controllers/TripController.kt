package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import pt.isel.ps.LI61N.g30.server.model.domain.Trip
import pt.isel.ps.LI61N.g30.server.api.input.AmendTrip
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.TripService
import pt.isel.ps.LI61N.g30.server.services.UserService

@RequestMapping("/trips", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class TripController(
        val userService : UserService,
        val authService: AuthService,
        val tripService: TripService
) {

    val log = LoggerFactory.getLogger(TripController::class.java)

    //TODO with DeferredResult
    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value = "/status")
    fun getLatestTripFromVehicle(
            @RequestParam(name = "vehicle") vehicle_id: Long
    ): ResponseEntity<Trip> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return ResponseEntity.ok(tripService.getLatestTrip(vehicle_id, user))
    }

    @Transactional
    @RequestMapping(method = [RequestMethod.POST], value = "/{trip_id}/amend")
    fun amendTrip(
            @PathVariable trip_id: Long,
            @RequestBody input: AmendTrip
    ): ResponseEntity<Void> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        tripService.amendTrip(trip_id, input.new_begin_toll, input.new_end_toll, user)
        return ResponseEntity.noContent().build()
    }

}




