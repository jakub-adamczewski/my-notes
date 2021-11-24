package com.example.mynotes.notes

import com.example.mynotes.auth.user.UserId
import com.example.mynotes.base.EntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface NotesRepository : JpaRepository<Note, EntityId> {

    fun findNoteByIdAndUserId(id: EntityId, userId: UserId): Note?

    fun findAllByUserIdOrderByTitle(userId: UserId): List<Note>

    @Query("SELECT n FROM Note n WHERE (n.userId = :userId) and (n.title LIKE CONCAT('%',:search,'%') OR n.content LIKE CONCAT('%',:search,'%')) ORDER BY n.title")
    fun findAllBySearchInTitleOrContentAndUserIdOrderByTitle(search: String, userId: UserId): List<Note>

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Note n SET n.title = :title, n.content = :content, n.updatedAt = CURRENT_TIMESTAMP WHERE n.id = :id and n.userId = :userId")
    fun updateNote(id: EntityId, userId: UserId /* = kotlin.String */, title: String, content: String): Int

    fun deleteNoteByIdAndUserId(id: EntityId, userId: UserId): Int
}