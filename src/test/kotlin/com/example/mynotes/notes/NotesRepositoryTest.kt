package com.example.mynotes.notes

import io.zonky.test.db.AutoConfigureEmbeddedDatabase

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@DataJpaTest
@RunWith(SpringRunner::class)
@AutoConfigureEmbeddedDatabase
class NotesRepositoryTest {

    @Autowired
    private lateinit var sut: NotesRepository

    @Test
    fun `SHOULD return note WHEN it belongs to user`() {
        val userId = "1234"
        val notes = listOf(
            Note("t1", "c1", userId),
            Note("t2", "c2", "4321"),
        )

        sut.saveAll(notes)
        val noteId = notes[0].id

        assert(sut.findNoteByIdAndUserId(noteId, userId) == notes[0])
    }

    @Test
    fun `SHOULD NOT return note WHEN it does NOT belong to user`() {
        val userId = "1234"
        val notes = listOf(
            Note("t1", "c1", userId),
            Note("t2", "c2", "4321"),
        )

        sut.saveAll(notes)
        val noteId = notes[1].id

        assert(sut.findNoteByIdAndUserId(noteId, userId) == null)
    }

    @Test
    fun `SHOULD return all notes for given user id`() {
        val userId = "1234"
        val notes = listOf(
            Note("t1", "c1", userId),
            Note("t2", "c2", userId),
            Note("t3", "c3", "sdfsdf"),
        )

        sut.saveAll(notes)

        assert(sut.findAllByUserIdOrderByTitle(userId) == notes[0, 1])
    }


    @Test
    fun `SHOULD filter notes by content and title`() {
        val userId = "1234"
        val notes = listOf(
            Note("cat", "dog", userId),
            Note("mouse", "cat", userId),
            Note("bird", "cow", userId),
        )

        sut.saveAll(notes)

        assert(sut.findAllBySearchInTitleOrContentAndUserIdOrderByTitle("cat", userId) == notes[0, 1])
        assert(sut.findAllBySearchInTitleOrContentAndUserIdOrderByTitle("dog", userId) == listOf(notes[0]))
        assert(sut.findAllBySearchInTitleOrContentAndUserIdOrderByTitle("mouse", userId) == listOf(notes[1]))
    }

    @Test
    fun `SHOULD update note and return updated rows number`() {
        val userId = "1234"
        val newTitle = "title1"
        val newContent = "content1"
        val notes = listOf(
            Note("cat", "dog", userId),
            Note("mouse", "cat", userId),
        )

        sut.saveAll(notes)
        val updatedRows = sut.updateNote(notes[0].id, userId, newTitle, newContent)


        assert(sut.findNoteByIdAndUserId(notes[0].id, userId) == notes[0].copy(title = newTitle, content = newContent))
        assert(updatedRows == 1)
    }

    @Test
    fun `SHOULD remove note`() {
        val userId = "1234"
        val notes = listOf(
            Note("cat", "dog", userId),
            Note("mouse", "cat", userId),
        )

        sut.saveAll(notes)
        val removedRows = sut.deleteNoteByIdAndUserId(notes[0].id, userId)


        assert(sut.findAllByUserIdOrderByTitle(userId) == listOf(notes[1]))
        assert(removedRows == 1)
    }

    private operator fun List<Note>.get(vararg indexes: Int): List<Note> =
        filterIndexed { index, _ -> index in indexes }
}