package pt.isel.ps.LI61N.g30.server.services

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import pt.isel.ps.LI61N.g30.server.api.controllers.VehicleController
import pt.isel.ps.LI61N.g30.server.model.domain.Trip
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository
import pt.isel.ps.LI61N.g30.server.services.clearing.ClearingService

@Service
class VehicleService(
        val vehicleRepository: VehicleRepository
){
    companion object {
        fun isVehicleFromOwner(vehicle: Vehicle, user: User): Boolean =
                vehicle.owner.id == user.id

        fun isVehicleFromTrip(trip: Trip, vehicle: Vehicle) =
                trip.vehicle.id == vehicle.id
    }

    fun getVehicles(user: User): List<Vehicle>{
        return vehicleRepository.findByOwner(user)
    }

    fun getVehicle(user: User, car_id: Long): Vehicle
            = vehicleRepository.findByOwnerAndId(user, car_id).orElseThrow { Exception("No vehicle found with the provided id.") }

    fun registerVehicle(plate: String, tier: Long, user: User): Vehicle {
        val vehicle = Vehicle(plate = plate, tier = tier, owner = user)
        return vehicleRepository.save(vehicle)
    }
}