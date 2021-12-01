package com.example.mynotes.auth.token.authenticator

import com.example.mynotes.base.AuthenticationException
import com.example.mynotes.auth.token.model.AuthToken
import com.example.mynotes.auth.token.model.MyNotesAuthentication
import com.example.mynotes.auth.user.model.MyNotesPrincipal
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier

class GoogleTokenAuthenticator(
    private val googleIdTokenVerifier: GoogleIdTokenVerifier
) : TokenAuthenticator {

    override val authProvider: AuthProvider = AuthProvider.GOOGLE

    override fun authenticate(token: AuthToken): MyNotesAuthentication = token.run {
        googleIdTokenVerifier.verify(userToken)?.let { googleIdToken ->
            googleIdToken.payload.run {
                MyNotesAuthentication(
                    principal = MyNotesPrincipal(
                        userId = subject,
                        firstName = get("name") as String,
                        familyName = get("family_name") as String,
                    ),
                    credentials = token
                )
            }
        } ?: throw AuthenticationException()
    }
}