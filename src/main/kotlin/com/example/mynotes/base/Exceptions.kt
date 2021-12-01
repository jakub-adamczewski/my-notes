package com.example.mynotes.base

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException

open class AuthenticationException(msg: String = "You are not authenticated.", cause: Throwable? = null) : AuthenticationException(msg, cause)

open class AuthorizationException(msg: String, cause: Throwable? = null) : AccessDeniedException(msg, cause)