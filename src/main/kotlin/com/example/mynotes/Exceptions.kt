package com.example.mynotes

class UserExistsException(email: String) :
    RuntimeException("Could not create user with email $email, he/she already exists.")

class UserNotExistsException(email: String) :
    RuntimeException("Could not login user with email $email, he/she doest not exist.")

class WrongPasswordException(email: String) :
    RuntimeException("Wrong password for user with email: $email.")

class EmailInvalidException(email: String) :
    CredentialInvalidException("Email $email is invalid.")

class PasswordInvalidException :
    CredentialInvalidException("Wrong password! It should be 8 characters long and contain: small letter, big letter, number, and special character")

abstract class CredentialInvalidException(override val message: String) : RuntimeException(message)
