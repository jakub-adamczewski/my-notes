package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface NotesRepository : JpaRepository<Note, EntityId> {

    @Query("SELECT n FROM Note n WHERE n.title LIKE CONCAT('%',:search,'%') OR n.content LIKE CONCAT('%',:search,'%') ORDER BY n.title")
    fun findAllBySearchInTitleOrContent(search: String): List<Note>

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Note n SET n.title = :title, n.content = :content, n.updatedAt = CURRENT_TIMESTAMP WHERE n.id = :id")
    fun updateNote(id: EntityId, title: String, content: String): Int
}