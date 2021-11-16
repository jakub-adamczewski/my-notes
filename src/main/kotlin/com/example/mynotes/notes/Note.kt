package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
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
    @Column(updatable = false, insertable = false)
    val id: EntityId = EntityId.randomUUID()

    @CreationTimestamp
    var createdAt: Date? = null

    @UpdateTimestamp
    var updatedAt: Date? = null
}