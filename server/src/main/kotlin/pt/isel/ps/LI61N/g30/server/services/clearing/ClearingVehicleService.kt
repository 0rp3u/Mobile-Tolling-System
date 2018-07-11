package pt.isel.ps.LI61N.g30.server.services.clearing

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import pt.isel.ps.LI61N.g30.server.model.domain.User
import pt.isel.ps.LI61N.g30.server.model.domain.Vehicle
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.VehicleRepository

@Service
class ClearingVehicleService(
        restTemplateBuilder: RestTemplateBuilder,
        val vehicleRepository: VehicleRepository
) {

    val vehiclesURI = "/vehicles"
    val restTemplate: RestTemplate = restTemplateBuilder.build()

    //TODO getVehiclesByUser
    fun getVehicles(user: User): List<Vehicle>{
        val url = String.format("%s%s", ClearingService.BASE_URI, vehiclesURI)
        return try{
            val vehicles = restTemplate.getForObject(url, Array<Vehicle>::class.java) ?: emptyArray()
            vehicles.asList()
        }catch (e: RestClientException){
            throw e
        }
        //TODO
        //vehicleRepository.findByOwner(user)
    }

    fun getVehicle(user: User, car_id: Long): Vehicle
            = vehicleRepository.findByOwnerAndId(user, car_id).orElseThrow { Exception("No vehicle found with the provided id.") }

    fun registerVehicle(vehicle: Vehicle): Vehicle {
        val url = String.format("%s%s", ClearingService.BASE_URI, vehiclesURI)
        return try{
            val res = restTemplate.exchange(url, HttpMethod.POST, HttpEntity(vehicle), Vehicle::class.java)
            res.body.let {
                if(it == null) throw Exception("Could not register vehicle")
            vehicleRepository.save(it)
            }
        }catch (e: RestClientException){
            throw e
        }
    }
}