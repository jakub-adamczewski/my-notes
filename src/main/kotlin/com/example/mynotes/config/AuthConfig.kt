package com.example.mynotes.config

import com.example.mynotes.auth.user.CurrentContextUser
import com.example.mynotes.auth.user.CurrentUser
import com.example.mynotes.auth.token.authenticator.TokenAuthenticatorDelegation
import com.example.mynotes.auth.token.authenticator.GoogleTokenAuthenticator
import com.example.mynotes.auth.token.authenticator.TokenAuthenticator
import com.example.mynotes.auth.token.filter.BearerTokenFilter
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
class AuthConfig {

    @Bean
    fun googleTokenAuthenticator(googleIdTokenVerifier: GoogleIdTokenVerifier): GoogleTokenAuthenticator =
        GoogleTokenAuthenticator(googleIdTokenVerifier)

    @Bean
    fun tokenAuthenticators(googleTokenAuthenticator: GoogleTokenAuthenticator): List<TokenAuthenticator> =
        listOf(googleTokenAuthenticator)

    @Bean
    fun tokenAuthenticatorDelegation(authenticators: List<TokenAuthenticator>): TokenAuthenticatorDelegation =
        TokenAuthenticatorDelegation(authenticators)

    @Bean
    @Qualifier("bearer_filter")
    fun bearerTokenFilter(tokenAuthenticatorDelegation: TokenAuthenticatorDelegation): OncePerRequestFilter =
        BearerTokenFilter(tokenAuthenticatorDelegation)

    @Bean
    fun currentUser(): CurrentUser = CurrentContextUser()
}