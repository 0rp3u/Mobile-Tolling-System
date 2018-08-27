package pt.isel.ps.LI61N.g30.server.configuration

import com.bedatadriven.jackson.datatype.jts.JtsModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JtsJacksonConfig{

    @Bean
    fun jtsModule() = JtsModule()
}