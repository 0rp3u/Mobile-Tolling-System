package pt.isel.ps.LI61N.g30.server.api.controllers

import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import pt.isel.ps.LI61N.g30.server.model.domain.Toll
import pt.isel.ps.LI61N.g30.server.services.TollService
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingTollService
import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RequestMapping("/tolls", produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class TollController(
        val tollService: TollService
) {

    //todo async
    @Transactional(readOnly = true)
    @RequestMapping(method = [RequestMethod.GET], value="/nearest")
    fun findNearestTolls(
            @RequestParam("lat") latitude: Double,
            @RequestParam("lon") longitude: Double,
            @RequestParam("number") nTolls: Optional<Int>
    ): ResponseEntity<List<Long>>{
        return ResponseEntity.ok( tollService.getNearestTolls(GeoLocation(latitude, longitude), nTolls))
    }

}