package com.example.mynotes.notes.db

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
data class Note(
    var title: String,
    var content: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    var id: Long? = null

    @CreationTimestamp
    var createdAt: Date? = null

    @UpdateTimestamp
    var updatedAt: Date? = null
}