package com.example.mynotes.auth.user.model

import com.example.mynotes.auth.user.UserId
import java.security.Principal

data class MyNotesPrincipal(
    val userId: UserId,
    val firstName: String,
    val familyName: String,
) : Principal {
    override fun getName(): String = "$firstName $familyName"
}