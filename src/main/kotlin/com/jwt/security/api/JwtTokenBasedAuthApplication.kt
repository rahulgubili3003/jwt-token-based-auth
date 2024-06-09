package com.jwt.security.api

import com.jwt.security.api.entity.Users
import com.jwt.security.api.repository.UsersRepository
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.stream.*

@SpringBootApplication
class JwtTokenBasedAuthApplication(private val usersRepository: UsersRepository) {
	@PostConstruct
	fun initUsers() {
		val users: List<Users> = Stream.of(
			Users( 1, "javatechie", "password", "javatechie@gmail.com"),
			Users( 2, "user1", "pwd1", "user1@gmail.com"),
			Users( 3, "user2", "pwd2", "user2@gmail.com"),
			Users( 4, "user3", "pwd3", "user3@gmail.com")
		).collect(Collectors.toList())
		usersRepository.saveAll(users)
	}
}

fun main(args: Array<String>) {
	runApplication<JwtTokenBasedAuthApplication>(*args)
}


