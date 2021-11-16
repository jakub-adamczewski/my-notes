package com.example.mynotes.user

import com.example.mynotes.CredentialInvalidException
import com.example.mynotes.UserExistsException
import com.example.mynotes.user.db.User
import com.example.mynotes.user.model.AuthRequest
import com.example.mynotes.user.model.AuthResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody authRequest: AuthRequest): User = try {
        userService.createNew(authRequest)
    } catch (e: UserExistsException) {
        throw ResponseStatusException(HttpStatus.CONFLICT, e.message)
    } catch (e: CredentialInvalidException) {
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): AuthResponse

}