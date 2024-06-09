package com.jwt.security.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor

@Entity
@Table(name = "users-db")
@NoArgsConstructor
@AllArgsConstructor
@Getter
data class Users(
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    val id: Long? = null,

    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "email")
    val email: String
)
