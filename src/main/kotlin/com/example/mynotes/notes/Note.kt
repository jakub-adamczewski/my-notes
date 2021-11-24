package com.example.mynotes.notes

import com.example.mynotes.auth.user.UserId
import com.example.mynotes.base.EntityId
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
data class Note(
    var title: String,
    var content: String,
    @JsonIgnore
    var userId: UserId
) {
    @Id
    @Column(updatable = false, insertable = false)
    val id: EntityId = EntityId.randomUUID()

    @CreationTimestamp
    var createdAt: Date? = null

    @UpdateTimestamp
    var updatedAt: Date? = null
}