package pt.isel.ps.LI61N.g30.server.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.PrintWriter
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.filter.CommonsRequestLoggingFilter



@Configuration
@EnableWebSecurity
class SecurityConfigurer(
        val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter(false) {

    @Autowired
    @Throws(Exception::class)
    fun configAuthentication(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Override
    override fun configure(http: HttpSecurity) {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/users/authentication")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .realmName("user realm")
                    .and()
                //.formLogin().loginPage("/userauthentication").permitAll().and()
                .logout()
                    //.permitAll()
                    .and()
                .csrf().disable()
    }

    @Bean
    fun requestLoggingFilter(): CommonsRequestLoggingFilter {
        val loggingFilter = CommonsRequestLoggingFilter()
        loggingFilter.setIncludeClientInfo(true)
        loggingFilter.setIncludeQueryString(true)
        loggingFilter.setIncludePayload(true)
        return loggingFilter
    }
}
