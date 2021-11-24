package com.example.mynotes.auth.token.authenticator

import com.example.mynotes.auth.token.model.AuthToken
import com.example.mynotes.auth.token.model.MyNotesAuthentication
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class TokenAuthenticatorDelegation(tokenAuthenticators: List<TokenAuthenticator>) :
    TokenAuthenticator {

    override val authProvider: AuthProvider
        get() = throw Exception("Could not get auth provider from auth delegation.")

    private val authenticators: Map<AuthProvider, TokenAuthenticator> =
        tokenAuthenticators.associateBy { it.authProvider }

    override fun authenticate(token: AuthToken): MyNotesAuthentication =
        token.let {
            authenticators[it.authProvider]
                ?.authenticate(it)
                ?: throw WrongProviderException(it.authProvider)
        }

    class WrongProviderException(provider: AuthProvider) :
        ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Could not find authenticator for requested authenticationProvider $provider"
        )
}