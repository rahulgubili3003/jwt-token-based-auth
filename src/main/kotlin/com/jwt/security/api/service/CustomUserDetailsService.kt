package com.jwt.security.api.service

import com.jwt.security.api.repository.UsersRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Slf4j
class CustomUserDetailsService(private val usersRepository: UsersRepository): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = usersRepository.findByUsername(username)
        return User(user.username, user.password, ArrayList())
    }
}