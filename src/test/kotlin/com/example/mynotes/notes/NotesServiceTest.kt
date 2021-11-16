package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
import io.mockk.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import java.util.*

internal class NotesServiceTest {

    private val notesRepository = mockk<NotesRepository>(relaxed = true)
    private lateinit var sut: NotesService

    private val id = EntityId.randomUUID()
    private val title = "My cat"
    private val content = "My dog"

    @BeforeEach
    fun setUp() {
        every { notesRepository.save(any()) } returns Note(title, content)

        sut = NotesService(notesRepository)
    }

    @Test
    fun `SHOULD return null WHEN trying to get not existing notes`(){
        every { notesRepository.findById(id) } returns Optional.empty()

        val effect = sut.findById(id)

        assert(effect == null)
    }

    @Test
    fun `SHOULD return note WHEN trying to get it`(){
        val testNote = Note(title, content)
        every { notesRepository.findById(id) } returns Optional.of(testNote)

        val effect = sut.findById(id)

        assert(effect == testNote)
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
}