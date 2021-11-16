package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
import org.springframework.stereotype.Service

@Service
class NotesService(private val notesRepository: NotesRepository) {

    fun findById(id: EntityId): Note? = notesRepository.findById(id).orElse(null)

    fun findAll(): List<Note> = notesRepository.findAll()

    fun findAllBySearch(search: String): List<Note> = notesRepository.findAllBySearchInTitleOrContent(search)

    fun save(title: String, content: String): Note = notesRepository.save(Note(title, content))

    fun update(id: EntityId, title: String, content: String): Boolean {
        val updatedEntities = notesRepository.updateNote(id, title, content)
        return updatedEntities > 0
    }

    fun deleteById(id: EntityId) = notesRepository.deleteById(id)
}