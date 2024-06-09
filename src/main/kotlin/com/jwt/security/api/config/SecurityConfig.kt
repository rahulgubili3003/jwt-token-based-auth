package com.jwt.security.api.config

import com.jwt.security.api.filter.JwtFilter
import com.jwt.security.api.repository.UsersRepository
import com.jwt.security.api.service.CustomUserDetailsService
import com.jwt.security.api.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val usersRepository: UsersRepository,
) {
    @Bean
    fun jwtFilter(): JwtFilter {
        return JwtFilter(
            jwtUtil = JwtUtil(),
            customUserDetailsService = customUserDetailsService()
        )
    }

    @Bean
    fun customUserDetailsService() = CustomUserDetailsService(usersRepository)

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() } // Disables CSRF protection
            .authorizeHttpRequests { auth -> auth.requestMatchers("/auth/authenticate").permitAll().anyRequest().authenticated() }
            .sessionManagement { session -> session.disable() }
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}