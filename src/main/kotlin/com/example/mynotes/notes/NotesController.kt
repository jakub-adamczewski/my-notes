package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
import com.example.mynotes.notes.model.NoteRequestBody
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/notes")
class NotesController(private val notesService: NotesService) {

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: EntityId): Note = notesService.findById(id)
        ?: throw NoteNotFoundException()

    @GetMapping
    fun getAllBySearch(@RequestParam search: String?): List<Note> = search?.let {
        notesService.findAllBySearch(it)
    } ?: notesService.findAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addNote(@RequestBody noteRequestBody: NoteRequestBody): Note {
        if (noteRequestBody.let { it.title.isBlank() && it.content.isBlank() }) {
            throw EmptyNoteException()
        }
        return notesService.save(noteRequestBody.title, noteRequestBody.content)
    }

    @PatchMapping("/{id}")
    fun editNote(@PathVariable id: EntityId, @RequestBody editNoteRequestBody: NoteRequestBody) {
        val updated = notesService.update(id, editNoteRequestBody.title, editNoteRequestBody.content)
        if (!updated) throw NoteNotFoundException()
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: EntityId) {
        val deleted = notesService.deleteById(id)
        if (!deleted) throw NoteNotFoundException()
    }

    inner class EmptyNoteException : ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not create empty note.")
    inner class NoteNotFoundException : ResponseStatusException(HttpStatus.NOT_FOUND, "This note does not exist.")
}