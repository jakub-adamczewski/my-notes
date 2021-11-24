package com.example.mynotes.notes

import com.example.mynotes.auth.user.CurrentUser
import com.example.mynotes.base.EntityId
import org.springframework.stereotype.Service

@Service
class NotesService(
    private val notesRepository: NotesRepository,
    private val currentUser: CurrentUser
) {

    fun findById(id: EntityId): Note? =
        notesRepository.findNoteByIdAndUserId(id, currentUser.userId)

    fun findAll(): List<Note> =
        notesRepository.findAllByUserIdOrderByTitle(currentUser.userId)

    fun findAllBySearch(search: String): List<Note> =
        notesRepository.findAllBySearchInTitleOrContentAndUserIdOrderByTitle(search, currentUser.userId)

    fun save(title: String, content: String): Note =
        notesRepository.save(Note(title, content, currentUser.userId))

    fun update(id: EntityId, title: String, content: String): Boolean =
        notesRepository.updateNote(id, currentUser.userId, title, content).let { updatedRows ->
            updatedRows > 0
        }

    fun deleteById(id: EntityId): Boolean =
        notesRepository.deleteNoteByIdAndUserId(id, currentUser.userId).let { deletedRows ->
            deletedRows > 0
        }
}