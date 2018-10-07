package pt.isel.ps.LI61N.g30.server.api.controllers

import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import pt.isel.ps.LI61N.g30.server.api.input.InputModelVehicle
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import pt.isel.ps.LI61N.g30.server.model.domain.VehicleType
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository
import pt.isel.ps.LI61N.g30.server.services.AuthService
import pt.isel.ps.LI61N.g30.server.services.UserService
import pt.isel.ps.LI61N.g30.server.services.VehicleService
import java.net.URI
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RequestMapping("/vehicles", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class VehicleController(
        val authService: AuthService,
        val vehicleRepository: VehicleRepository,
        val vehicleService: VehicleService,
        val userService: UserService
) {

    val log = LoggerFactory.getLogger(VehicleController::class.java)

//    @Transactional(readOnly = true)
//    @RequestMapping(method = [RequestMethod.GET])
//    fun getUserVehicles(): DeferredResult<ResponseEntity<List<Vehicle>>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        val result = DeferredResult<ResponseEntity<List<Vehicle>>>(TimeUnit.SECONDS.toMillis(10))
//        CompletableFuture
//                .supplyAsync( { vehicleService.getVehicles(user)} )
//                .thenAccept( { result.setResult(ResponseEntity.ok(it))} )
//        return result
//    }

    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET])
    fun getUserVehicles(
            @RequestParam(value="date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd kk:mm:ss.SSS") date: Date?
    ): ResponseEntity<List<Vehicle>> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return vehicleService.getVehicles(date, user).let { ResponseEntity.ok(it) }
    }

//    @Transactional
//    @RequestMapping(method = [RequestMethod.POST])
//    fun registerUserVehicle(
//            @RequestBody vehicle: InputModelVehicle
//    ): DeferredResult<ResponseEntity<Vehicle>> {
//        val userId = authService.authenticatedUser().id
//        val user = userService.getUserByid(userId)
//        log.info("Fetched user: ${user.login}")
//
//        val result = DeferredResult<ResponseEntity<Vehicle>>(TimeUnit.SECONDS.toMillis(10))
//        CompletableFuture
//                .supplyAsync( { vehicleService.registerVehicle(vehicle.plate, vehicle.tier, user)} )
//                .thenAccept( { result.setResult(ResponseEntity.created(URI.create("")).body(it))} )
//        return result
//    }

    @Transactional
    @RequestMapping(method = [RequestMethod.POST])
    fun registerUserVehicle(
            @RequestBody vehicle: InputModelVehicle
    ): ResponseEntity<Vehicle> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return vehicleService.registerVehicle(vehicle.plate, vehicle.tier, user).let {
            ResponseEntity.created(URI.create("/vehicles/${it.id}")).body(it)
        }
    }

    @Transactional
    @RequestMapping(method = [RequestMethod.GET], value = "/{vehicle_id}")
    fun getUserVehicle(
            @RequestBody vehicle_id: Long
    ): ResponseEntity<Vehicle> {
        val userId = authService.authenticatedUser().id
        val user = userService.getUserByid(userId)
        log.info("Fetched user: ${user.login}")

        return vehicleService.getVehicle(user, vehicle_id).let {
            ResponseEntity.ok(it)
        }
    }

}