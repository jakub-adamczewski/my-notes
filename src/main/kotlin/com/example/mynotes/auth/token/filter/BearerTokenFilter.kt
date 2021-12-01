package com.example.mynotes.auth.token.filter

import com.example.mynotes.base.AuthenticationException
import com.example.mynotes.auth.token.BearerTokenExtractor
import com.example.mynotes.auth.token.authenticator.TokenAuthenticator
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BearerTokenFilter(
    private val tokenAuthenticator: TokenAuthenticator
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            BearerTokenExtractor.extractToken(request)
                ?.let { tokenAuthenticator.authenticate(it) }
                ?.let { SecurityContextHolder.getContext().authentication = it }
        } catch (e: AuthenticationException){
            logger.debug("Could not authenticate user", e)
        }

        filterChain.doFilter(request, response)
    }
}