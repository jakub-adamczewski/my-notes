package com.example.mynotes.util

import java.util.regex.Pattern

object CredentialsValidator {

    fun emailValid(email: String): Boolean = email.run {
        email.isNotEmpty() &&
                contains(Regex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}\$"))
    }

    fun passwordValid(password: String): Boolean = password.run {
        length >= 8 &&
                contains(Regex("[A-Z]")) &&
                contains(Regex("[a-z]")) &&
                contains(Regex("[0-9]")) &&
                contains(Regex("[^a-z0-9]", RegexOption.IGNORE_CASE))
    }
}