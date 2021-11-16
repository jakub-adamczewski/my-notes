package com.example.mynotes.user

import com.example.mynotes.*
import com.example.mynotes.user.db.User
import com.example.mynotes.user.db.UserRepository
import com.example.mynotes.user.model.AuthRequest
import com.example.mynotes.util.CredentialsValidator.emailValid
import com.example.mynotes.util.CredentialsValidator.passwordValid
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createNew(authRequest: AuthRequest): User = authRequest.let { request ->
        if (!emailValid(request.email)) {
            throw EmailInvalidException(request.email)
        }
        if (!passwordValid(request.email)) {
            throw PasswordInvalidException()
        }
        userRepository.run {
            if (existsByEmail(request.email)) {
                throw UserExistsException(request.email)
            }
            save(User(request.email, passwordEncoder.encode(request.password)))
        }
    }

    fun login(authRequest: AuthRequest): User = authRequest.let { request ->
        userRepository.run {
            userRepository.findByEmail(request.email)?.let { user ->
                if (passwordEncoder.encode(request.password) != user.password) {
                    throw WrongPasswordException(request.email)
                }
                user
            } ?: throw UserNotExistsException(request.email)
        }
    }
}