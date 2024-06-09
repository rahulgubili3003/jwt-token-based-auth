package com.jwt.security.api.repository

import com.jwt.security.api.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: JpaRepository<Users, Long> {
    fun findByUsername(username: String?): Users
}