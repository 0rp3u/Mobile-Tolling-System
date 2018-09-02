package pt.isel.ps.LI61N.g30.server

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Point
import org.hibernate.SessionFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.transaction.annotation.Transactional
import pt.isel.ps.LI61N.g30.server.model.domain.Toll
import pt.isel.ps.LI61N.g30.server.model.domain.repositories.TollRepository
import java.sql.SQLException



@SpringBootApplication
@EnableAsync
class ServerApplication{

    @Autowired
    lateinit var tollRepository: TollRepository

    val log = LoggerFactory.getLogger(ServerApplication::class.java)

    @Transactional
    @Bean
    fun initialize(): CommandLineRunner = CommandLineRunner {
        log.info("Initialize ---------------")
        val geometryFactory = GeometryFactory()
        //Polygon
        val poly = geometryFactory.createPolygon(arrayOf(
                Coordinate(-9.11488652229309,38.75673549392286),
                Coordinate(-9.115369319915771,38.755781710737885),
                Coordinate(-9.113599061965942,38.75527971446844),
                Coordinate(-9.113116264343262,38.75627115370124),
                Coordinate(-9.11488652229309,38.75673549392286)
        ))
        poly.srid = 4326
        val toll = Toll.create(1,"road below", true, geometryFactory.createPoint(Coordinate(-9.1129222, 38.7555662)), poly)
        val toll2 = Toll.create(2,"isel parking lot", true, geometryFactory.createPoint(Coordinate(-9.1143576, 38.755951)), poly)
        //val toll3 = Toll.create(3,"alameda", "NORMAL", geometryFactory.createPoint(Coordinate(-9.1254526, 38.7338987)))
        //civil isel Coordinate(-9.1155807, 38.7562773)
        toll.entry_area?.srid = 4326
        toll2.entry_area?.srid = 4326
        toll.geolocation?.srid = 4326
        toll2.geolocation?.srid = 4326
        //toll3.geolocation?.srid = 4326
        tollRepository.save(toll)
        tollRepository.save(toll2)
        //tollRepository.save(toll3)

        log.info("Finished Init ---------------")
    }

}

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}