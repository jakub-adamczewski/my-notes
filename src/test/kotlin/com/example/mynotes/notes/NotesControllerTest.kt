package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
import com.example.mynotes.notes.base.BaseMockMvcTest
import com.example.mynotes.notes.base.SecurityTestData
import com.example.mynotes.notes.model.NoteRequestBody
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@WebMvcTest(NotesController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class NotesControllerTest : BaseMockMvcTest() {

    @MockkBean
    lateinit var notesService: NotesService

    @Test
    fun `SHOULD return note WHEN it exists`() {
        val testNote = Note("title1", "content1", SecurityTestData.USER_ID)

        every { notesService.findById(testNote.id) } returns testNote

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/notes/${testNote.id}")
                .contextPath("/api")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpectProperValue(testNote)
    }

    @Test
    fun `SHOULD return not found WHEN note does not exists`() {
        val noteId = EntityId.randomUUID()

        every { notesService.findById(noteId) } returns null

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/notes/${noteId}")
                .contextPath("/api")
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `get all notes`() {
        val allNotes = listOf(
            Note("title1", "content1", SecurityTestData.USER_ID),
            Note("title2", "content2", SecurityTestData.USER_ID),
        )

        every { notesService.findAll() } returns allNotes

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes").contextPath("/api"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpectProperValues(allNotes)
    }

    @Test
    fun `search notes`() {
        val search = "dog"
        val searchedNotes = listOf(
            Note("dog1", "content1", SecurityTestData.USER_ID),
            Note("title2", "dog2", SecurityTestData.USER_ID),
        )
        every { notesService.findAllBySearch(search) } returns searchedNotes

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/notes")
                .contextPath("/api")
                .param("search", search)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpectProperValues(searchedNotes)
    }

    @Test
    fun `SHOULD create note WHEN it has title`() {
        val (title, content) = "title1" to "content1"
        val requestBody = NoteRequestBody(title, content)
        val returnedNote = Note(title, content, SecurityTestData.USER_ID)

        every { notesService.save(title, content) } returns returnedNote

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/notes")
                .contextPath("/api")
                .jsonBody(requestBody)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpectProperValue(returnedNote)
    }

    @Test
    fun `SHOULD not create note WHEN response body has blank title and content`() {
        val (title, content) = " " to " "
        val requestBody = NoteRequestBody(title, content)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/notes")
                .contextPath("/api")
                .jsonBody(requestBody)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `SHOULD edit note WHEN it exists`() {
        val noteId = EntityId.randomUUID()
        val (title, content) = "title1" to "content1"
        val requestBody = NoteRequestBody(title, content)

        every { notesService.update(noteId, title, content) } returns true

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/notes/${noteId}")
                .contextPath("/api")
                .jsonBody(requestBody)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `SHOULD return not found WHEN note does not exists while editing`() {
        val noteId = EntityId.randomUUID()
        val (title, content) = "title1" to "content1"
        val requestBody = NoteRequestBody(title, content)

        every { notesService.update(noteId, title, content) } returns false

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/notes/${noteId}")
                .contextPath("/api")
                .jsonBody(requestBody)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `SHOULD remove note WHEN it exists`() {
        val noteId = EntityId.randomUUID()

        every { notesService.deleteById(noteId) } returns true

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/notes/${noteId}")
                .contextPath("/api")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `SHOULD return not found WHEN note does not exists while removing`() {
        val noteId = EntityId.randomUUID()

        every { notesService.deleteById(noteId) } returns false

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/notes/${noteId}")
                .contextPath("/api")
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    private fun ResultActions.andExpectProperValue(note: Note) = this
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(note.title))

    private fun ResultActions.andExpectProperValues(notes: List<Note>) = this
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(notes[0].title))
        .andExpect(MockMvcResultMatchers.jsonPath("$[${notes.lastIndex}].title").value(notes.last().title))
}