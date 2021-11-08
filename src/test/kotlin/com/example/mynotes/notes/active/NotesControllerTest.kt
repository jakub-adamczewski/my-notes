package com.example.mynotes.notes.active

import com.example.mynotes.notes.Note
import com.example.mynotes.notes.NotesService
import com.example.mynotes.notes.NotesStore.notes
import com.example.mynotes.notes.model.NoteRequestBody
import com.google.gson.Gson
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@WebMvcTest(NotesController::class)
internal class NotesControllerTest {

    private val title = "hakuna"
    private val content = "matata"

    @MockkBean
    lateinit var notesService: NotesService

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `get all notes`() {
        val returnedNotes = notes

        every { notesService.findAll() } returns returnedNotes

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes").contextPath("/api"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpectProperValues(returnedNotes)
    }

    @Test
    fun `search notes`() {
        val search = "dog"
        val returnedNotes = notes.filter { it.title.contains(search) || it.content.contains(search) }

        every { notesService.findAllBySearch(search) } returns returnedNotes

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes").contextPath("/api").param("search", search))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpectProperValues(returnedNotes)
    }

    @Test
    fun `SHOULD create note WHEN it has title`() {
        val requestBody = NoteRequestBody(title, content)
        val returnedNote = Note(title, content).apply { id = 5L }

        every { notesService.save(title, content) } returns returnedNote

        mockMvc.perform(MockMvcRequestBuilders.post("/api/notes").contextPath("/api").jsonBody(requestBody))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpectProperValue(returnedNote)
    }

    @Test
    fun `SHOULD not create note WHEN it blank title and content`() {
        val (title, content) = "    " to "  "
        val requestBody = NoteRequestBody(title, content)

        mockMvc.perform(MockMvcRequestBuilders.post("/api/notes").contextPath("/api").jsonBody(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `SHOULD edit note WHEN it exists`() {
        val noteId = 5L
        val requestBody = NoteRequestBody(title, content)

        every { notesService.update(noteId, title, content) } returns true

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/notes/${noteId}").contextPath("/api").jsonBody(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `SHOULD return not found WHEN note does not exists while editing`() {
        val noteId = 5L
        val requestBody = NoteRequestBody(title, content)

        every { notesService.update(noteId, title, content) } returns false

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/notes/${noteId}").contextPath("/api").jsonBody(requestBody))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `delete note`() {
        val noteId = 5L

        every { notesService.deleteById(noteId) } just runs

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/${noteId}").contextPath("/api"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private fun ResultActions.andExpectProperValue(note: Note) = this
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(note.title))

    private fun ResultActions.andExpectProperValues(notes: List<Note>) = this
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(notes[0].title))
        .andExpect(MockMvcResultMatchers.jsonPath("$[${notes.lastIndex}].title").value(notes.last().title))

    private fun MockHttpServletRequestBuilder.jsonBody(body: String): MockHttpServletRequestBuilder =
        content(body).contentType("application/json")

    private fun MockHttpServletRequestBuilder.jsonBody(body: Any): MockHttpServletRequestBuilder =
        jsonBody(Gson().toJson(body).toString())


}