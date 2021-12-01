package com.example.mynotes.auth.user

import com.example.mynotes.base.AuthenticationException
import com.example.mynotes.auth.token.model.MyNotesAuthentication
import org.springframework.security.core.context.SecurityContextHolder

class CurrentContextUser : CurrentUser {

    override val authentication: MyNotesAuthentication
        get() = SecurityContextHolder.getContext()?.authentication as? MyNotesAuthentication
            ?: throw AuthenticationException()
}