package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
import com.example.mynotes.notes.model.NoteRequestBody
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/notes")
class NotesController(private val notesService: NotesService) {

    @GetMapping
    fun getAllBySearch(@RequestParam search: String?): List<Note> = search?.let {
        notesService.findAllBySearch(it)
    } ?: notesService.findAll()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: EntityId): Note = notesService.findById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Note with id $id does not exist.")

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addNote(@RequestBody noteRequestBody: NoteRequestBody): Note {
        if (noteRequestBody.let { it.title.isBlank() && it.content.isBlank() }) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not create empty note")
        }
        return notesService.save(noteRequestBody.title, noteRequestBody.content)
    }

    @PatchMapping("/{id}")
    fun editNote(@PathVariable id: EntityId, @RequestBody editNoteRequestBody: NoteRequestBody) {
        val updated = notesService.update(id, editNoteRequestBody.title, editNoteRequestBody.content)
        if (!updated) throw ResponseStatusException(HttpStatus.NOT_FOUND, "This note does not exist")
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: EntityId) = notesService.deleteById(id)

}