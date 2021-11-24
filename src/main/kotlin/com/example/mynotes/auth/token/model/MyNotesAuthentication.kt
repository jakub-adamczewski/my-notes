package com.example.mynotes.auth.token.model

import com.example.mynotes.auth.user.model.MyNotesPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class MyNotesAuthentication(
    val principal: MyNotesPrincipal,
    val credentials: AuthToken
) : UsernamePasswordAuthenticationToken(principal, credentials)
