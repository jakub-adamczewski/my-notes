package com.example.mynotes.notes

import com.example.mynotes.notes.db.Note
//import com.example.mynotes.user.db.User
//import org.springframework.security.crypto.password.PasswordEncoder

object TestDataStore {

    val notes = listOf(
        Note("cat", "dog"),
        Note("rat", "cat ble"),
    )

//    fun getUsers(passwordEncoder: PasswordEncoder) = listOf(
//        User("jakub@gmail.com", passwordEncoder.encode("Hh4@kR$8df")),
//        User("marek@wp.pl", passwordEncoder.encode("45$^HKsfowijf")),
//    )
}