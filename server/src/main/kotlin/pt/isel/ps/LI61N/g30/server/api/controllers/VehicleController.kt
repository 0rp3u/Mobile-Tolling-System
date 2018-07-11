package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import pt.isel.ps.LI61N.g30.server.model.domain.VehicleType
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingUserService
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingVehicleService
import java.net.URI
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RequestMapping("/vehicles", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class VehicleController(
        val userService : ClearingUserService,
        val authService: AuthService,
        val vehicleRepository: VehicleRepository,
        val vehicleService: ClearingVehicleService
) {

    val log = LoggerFactory.getLogger(VehicleController::class.java)

    data class InputModelVehicle(
            val plate: String,
            val tier: Long
    )

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET])
    fun getUserVehicles(): DeferredResult<ResponseEntity<List<Vehicle>>> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        val result = DeferredResult<ResponseEntity<List<Vehicle>>>(TimeUnit.SECONDS.toMillis(10))
        CompletableFuture
                .supplyAsync( { vehicleService.getVehicles(user)} )
                .thenAccept( { result.setResult(ResponseEntity.ok(it))} )
        return result
    }

    @Transactional
    @RequestMapping(method = [RequestMethod.POST])
    fun registerUserVehicle(
            @RequestBody vehicle: InputModelVehicle
    ): DeferredResult<ResponseEntity<Vehicle>> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")
        val newVehicle = Vehicle(plate = vehicle.plate, owner = user, tier = vehicle.tier)
        val result = DeferredResult<ResponseEntity<Vehicle>>(TimeUnit.SECONDS.toMillis(10))
        CompletableFuture
                .supplyAsync( { vehicleService.registerVehicle(newVehicle)} )
                .thenAccept( { result.setResult(ResponseEntity.created(URI.create("")).body(it))} )
        return result
    }


}