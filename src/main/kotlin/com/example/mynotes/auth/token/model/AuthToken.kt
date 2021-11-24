package com.example.mynotes.auth.token.model

import com.example.mynotes.auth.token.authenticator.AuthProvider

data class AuthToken(val userToken: String, val authProvider: AuthProvider)
