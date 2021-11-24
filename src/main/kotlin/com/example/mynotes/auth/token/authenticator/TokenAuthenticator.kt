package com.example.mynotes.auth.token.authenticator

import com.example.mynotes.auth.token.model.AuthToken
import com.example.mynotes.auth.token.model.MyNotesAuthentication

interface TokenAuthenticator {
    val authProvider: AuthProvider
    fun authenticate(token: AuthToken): MyNotesAuthentication
}