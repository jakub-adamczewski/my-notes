package com.example.mynotes.auth.token

import com.example.mynotes.auth.token.authenticator.AuthProvider
import com.example.mynotes.auth.token.model.AuthToken
import org.springframework.http.HttpHeaders
import javax.servlet.http.HttpServletRequest

object BearerTokenExtractor {

    const val AUTH_PROVIDER_HEADER = "X-AUTH-PROVIDER"
    const val BEARER_NAME = "Bearer"

    fun extractToken(request: HttpServletRequest): AuthToken? = request.run {
        getHeader(HttpHeaders.AUTHORIZATION)?.let { authHeader ->
            getHeader(AUTH_PROVIDER_HEADER)?.let { authProviderHeader ->
                if (authHeader.trim().startsWith(BEARER_NAME)) {
                    AuthToken(
                        userToken = authHeader.substring(BEARER_NAME.length).trim(),
                        authProvider = AuthProvider.valueOf(authProviderHeader.trim().uppercase())
                    )
                } else {
                    null
                }
            }
        }
    }
}