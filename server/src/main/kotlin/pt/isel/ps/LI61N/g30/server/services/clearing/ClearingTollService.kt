package pt.isel.ps.LI61N.g30.server.services.clearing

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate;
import pt.isel.ps.LI61N.g30.server.model.domain.Toll

import pt.isel.ps.LI61N.g30.server.utils.GeoLocation
import java.util.*

@Service
class ClearingTollService(
        restTemplateBuilder: RestTemplateBuilder
){
    private val MAX_TOLLS: Int = 90
    private val tollsURI = "/tolls"
    private val restTemplate: RestTemplate = restTemplateBuilder.build()


    fun getTolls(location: GeoLocation, nTolls: Optional<Int>): List<Toll> {
        return if(nTolls.isPresent){
            getTolls(location, nTolls.get())
        }else{
            getTolls(location)
        }
    }

    private fun getTolls(location: GeoLocation, nTolls: Int = MAX_TOLLS): List<Toll>{
        val url = String.format("%s%s", ClearingService.BASE_URI, tollsURI)
        return try{
            val tolls = restTemplate.getForObject(url, Array<Toll>::class.java) ?: emptyArray()
            tolls.asList()
        }catch (e: RestClientException){
            throw e
        }
    }

//    private fun getTolls(location: GeoLocation, nTolls: Int = MAX_TOLLS): ListenableFuture<List<Toll>>{
//        val url = String.format("%s%s", ClearingService.BASE_URI, tollsURI)
//        return try{
//            val tolls = restTemplate.getForObject(url, Array<Toll>::class.java) ?: emptyArray()
//            AsyncResult.forValue(tolls.asList())
//        }catch (e: RestClientException){
//            AsyncResult.forExecutionException(e)
//        }
//    }
}

//data class Clearing_Toll(
//        val name: String,
//        val toll_type: String,
//        val geolocation_latitude: Float,
//        val geolocation_longitude: Float,
//        val azimuth: Float,
//        val region: String,
//        val road: String
//)