package com.example.mynotes.auth.user

import com.example.mynotes.auth.token.model.MyNotesAuthentication

interface CurrentUser {
    val authentication: MyNotesAuthentication
    val userId: UserId
        get() = authentication.principal.userId
}