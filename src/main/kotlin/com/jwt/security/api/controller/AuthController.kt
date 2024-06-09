package com.jwt.security.api.controller

import com.jwt.security.api.dto.OkResponse
import com.jwt.security.api.dto.request.AuthRequest
import com.jwt.security.api.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager) {

    @GetMapping("/get")
    fun getAuth(): ResponseEntity<OkResponse> {
        return ResponseEntity.ok(OkResponse())
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authRequest: AuthRequest): ResponseEntity<String> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.username,
                    authRequest.password
                ))
        } catch (e: Exception) {
            throw RuntimeException("An unexpected error occurred.")
        }
        val token = jwtUtil.generateToken(authRequest.username)
        return ResponseEntity.ok(token)
    }
}