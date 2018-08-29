package pt.isel.ps.LI61N.g30.server.api.input

import pt.isel.ps.LI61N.g30.server.model.domain.VehicleType

data class InputModelVehicle(
        val plate: String,
        val tier: VehicleType
)