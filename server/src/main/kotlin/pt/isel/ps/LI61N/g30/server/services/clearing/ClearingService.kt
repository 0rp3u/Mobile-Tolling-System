package pt.isel.ps.LI61N.g30.server.services.clearing

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClearingService{
    companion object {
        val BASE_URI = "http://localhost:3002"

        @JsonInclude(JsonInclude.Include.NON_NULL)
        data class UserDTO(
            val nif: Int,
            val name: String?,
            val password: String,
            val state: String,
            val contact: String?
        )

        data class VehicleDTO(
            val id: Int,
            val plate: String,
            val nif: Int,
            val category: String,
            val state: String
        )

        data class TollDTO(
            val id: Int,
            val name: String?,
            val toll_type: String,
            val geolocation_latitude: Float,
            val geolocation_longitude: Float,
            val azimuth: Float?,
            val region: String?,
            val road: String?
        )

        data class EventDTO(
            val id: Int,
            val direction: String?,
            val etc_id: String?,
            val timestamp: Date,
            val issuer: String?
        )
    }
}