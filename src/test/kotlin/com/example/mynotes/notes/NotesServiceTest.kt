package com.example.mynotes.notes

import com.example.mynotes.notes.db.Note
import com.example.mynotes.notes.db.NotesRepository
import io.mockk.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

internal class NotesServiceTest {

    private val notesRepository = mockk<NotesRepository>(relaxed = true)
    private lateinit var sut: NotesService

    private val id = 5L
    private val title = "My cat"
    private val content = "My dog"

    @BeforeEach
    fun setUp() {
        every { notesRepository.save(any()) } returns Note(title, content)
        every { notesRepository.getById(any()) } returns Note(title, content)

        sut = NotesService(notesRepository)
    }

    @Test
    fun `can find all notes`(){
        sut.findAll()

        verify(exactly = 1) { notesRepository.findAll() }
    }

    @Test
    fun `can find all by search`(){
        val search = "search text"

        sut.findAllBySearch(search)

        verify(exactly = 1) { notesRepository.findAllBySearchInTitleOrContent(search) }
    }

    @Test
    fun `SHOULD save note WHEN given title and content`(){
        sut.save(title, content)

        verify(exactly = 1) { notesRepository.save(Note(title, content)) }
    }

    @Test
    fun `SHOULD return false WHEN trying to edit not existing note`(){
        every { notesRepository.updateNote(id, title, content) } returns 0

        val effect = sut.update(id, title, content)

        assert(!effect)
    }

    @Test
    fun `SHOULD return true WHEN trying to edit existing note`(){
        every { notesRepository.updateNote(id, title, content) } returns 1

        val effect = sut.update(id, title, content)

        assert(effect)
    }

    @Test
    fun `can delete`(){
        sut.deleteById(id)

        verify(exactly = 1) { notesRepository.deleteById(id) }
    }

}