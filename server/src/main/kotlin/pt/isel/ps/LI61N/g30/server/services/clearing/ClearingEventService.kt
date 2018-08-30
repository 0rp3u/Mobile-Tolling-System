package pt.isel.ps.LI61N.g30.server.services.clearing

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.EventRepository

@Service
class ClearingEventService(
        restTemplateBuilder: RestTemplateBuilder,
        private val eventRepository: EventRepository
) {

    private val vehiclesURI = "/vehicles"
    private val restTemplate: RestTemplate = restTemplateBuilder.build()

//    fun getVehicles(user: User): List<Vehicle>{
//        val url = String.format("%s%s", ClearingService.BASE_URI, vehiclesURI)
//        return try{
//            val vehicles = restTemplate.getForObject(url, Array<Vehicle>::class.java) ?: emptyArray()
//            vehicles.asList()
//        }catch (e: RestClientException){
//            throw e
//        }
//        //TODO
//        //vehicleRepository.findByOwner(user)
//    }

//    fun getEvents(user: User, car_id: Long, initialDate: Date, finalDate: Date): Event
//            = eventRepository.findByOwnerAndId(user, car_id)

    fun registerTransaction(){

    }
}