package com.example.mynotes.notes.base

import com.example.mynotes.auth.user.CurrentUser
import com.example.mynotes.auth.token.authenticator.AuthProvider
import com.example.mynotes.auth.user.UserId
import com.example.mynotes.auth.token.model.AuthToken
import com.example.mynotes.auth.token.model.MyNotesAuthentication
import com.example.mynotes.auth.user.model.MyNotesPrincipal
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object SecurityTestData {

    @JvmStatic
    val USER_ID = "1234"

    @JvmStatic
    val JWT_BEARER_TOKEN =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    val USER_TOKEN = AuthToken(JWT_BEARER_TOKEN, AuthProvider.GOOGLE)

    val currentUser: CurrentUser
        get() = object : CurrentUser {
            override val authentication: MyNotesAuthentication
                get() = myNotesAuthentication
            override val userId: UserId
                get() = authentication.principal.userId
        }

    val myNotesAuthentication: MyNotesAuthentication
        get() = MyNotesAuthentication(
            principal = MyNotesPrincipal(
                userId = USER_ID,
                firstName = "Jan",
                familyName = "Nowak",
            ),
            credentials = USER_TOKEN
        )

    val bearerFilter: OncePerRequestFilter
        get() = object : OncePerRequestFilter() {
            override fun doFilterInternal(
                request: HttpServletRequest,
                response: HttpServletResponse,
                filterChain: FilterChain
            ) {
                filterChain.doFilter(request, response)
            }
        }
}