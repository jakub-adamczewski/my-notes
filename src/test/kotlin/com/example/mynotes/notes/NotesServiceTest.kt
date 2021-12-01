package com.example.mynotes.notes

import com.example.mynotes.auth.user.CurrentUser
import com.example.mynotes.base.EntityId
import io.mockk.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

internal class NotesServiceTest {

    private val notesRepository = mockk<NotesRepository>(relaxed = true)
    private val currentUser = mockk<CurrentUser>()
    private lateinit var sut: NotesService

    companion object {
        private val NOTE_ID = EntityId.randomUUID()
        private const val USER_ID = "1234"
    }

    @BeforeEach
    fun setUp() {
        every { currentUser.userId } returns USER_ID
        sut = NotesService(notesRepository, currentUser)
    }

    @Test
    fun `SHOULD return null WHEN trying to find not existing notes`() {
        every { notesRepository.findNoteByIdAndUserId(NOTE_ID, USER_ID) } returns null

        val effect = sut.findById(NOTE_ID)

        assert(effect == null)
    }

    @Test
    fun `SHOULD return note WHEN trying to find it`() {
        val testNote = Note("cat", "dog", USER_ID)
        every { notesRepository.findNoteByIdAndUserId(testNote.id, USER_ID) } returns testNote

        val effect = sut.findById(testNote.id)

        assert(effect == testNote)
    }

    @Test
    fun `SHOULD return true WHEN any note updated`() {
        every { notesRepository.updateNote(any(), any(), any(), any()) } returns 1

        val effect = sut.update(NOTE_ID, "test_title", "test_content")

        assert(effect)
    }

    @Test
    fun `SHOULD return false WHEN none note updated`() {
        every { notesRepository.updateNote(any(), any(), any(), any()) } returns 0

        val effect = sut.update(NOTE_ID, "test_title", "test_content")

        assert(!effect)
    }
}