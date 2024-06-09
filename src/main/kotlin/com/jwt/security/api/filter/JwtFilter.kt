package com.jwt.security.api.filter

import com.jwt.security.api.service.CustomUserDetailsService
import com.jwt.security.api.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val customUserDetailsService: CustomUserDetailsService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        var token: String? = null
        var userName: String? = null

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7)
            userName = jwtUtil.extractUsername(token)
        }

        if (userName != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(userName)

            if (jwtUtil.validateToken(token, userDetails)) {
                val usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details =
                    WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}