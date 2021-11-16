package com.example.mynotes.notes

import com.example.mynotes.notes.db.Note
import com.example.mynotes.notes.db.NotesRepository
import org.springframework.stereotype.Service

@Service
class NotesService(private val notesRepository: NotesRepository) {

    fun findAll(): List<Note> = notesRepository.findAll()

    fun findAllBySearch(search: String): List<Note> = notesRepository.findAllBySearchInTitleOrContent(search)

    fun save(title: String, content: String): Note = notesRepository.save(Note(title, content))

    fun update(id: Long, title: String, content: String): Boolean {
        val updatedEntities = notesRepository.updateNote(id, title, content)
        return updatedEntities > 0
    }

    fun deleteById(id: Long) = notesRepository.deleteById(id)
}