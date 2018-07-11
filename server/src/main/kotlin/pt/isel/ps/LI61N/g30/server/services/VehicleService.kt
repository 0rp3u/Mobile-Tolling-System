package pt.isel.ps.LI61N.g30.server.services

import org.springframework.stereotype.Service
import pt.isel.ps.LI61N.g30.server.model.domain.Trip
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository

@Service
class VehicleService(
        vehicleRepository: VehicleRepository
){
    companion object {
        fun isVehicleFromOwner(vehicle: Vehicle, user: User): Boolean =
                vehicle.owner.id == user.id

        fun isVehicleFromTrip(trip: Trip, vehicle: Vehicle) =
                trip.vehicle.id == vehicle.id
    }

}