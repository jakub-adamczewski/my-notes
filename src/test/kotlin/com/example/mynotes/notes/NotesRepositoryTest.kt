package com.example.mynotes.notes

import com.example.mynotes.base.EntityId
import com.example.mynotes.notes.NotesStore.notes
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

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

    @BeforeEach
    fun setUp() {
        sut.saveAll(notes)
    }

    @AfterEach
    fun tearDown() {
        sut.deleteAll()
    }

    @Test
    fun `SHOULD order notes by title`() {
        val loaded = sut.findAll()

        assert(loaded == notes.sortedBy { it.title })
    }

    @Test
    fun `SHOULD return first note WHEN only it contains search in content`() {
        val search = "dog"

        val loaded = sut.findAllBySearchInTitleOrContent(search)

        assert(loaded.size == 1 && loaded.first() == notes[0])
    }

    @Test
    fun `SHOULD return second note WHEN only it contains search in title`() {
        val search = "rat"

        val loaded = sut.findAllBySearchInTitleOrContent(search)

        assert(loaded.size == 1 && loaded.first() == notes[1])
    }

    @Test
    fun `SHOULD return both notes WHEN they contain search in title or content`() {
        val search = "cat"

        val loaded = sut.findAllBySearchInTitleOrContent(search)

        assert(loaded.size == 2 && loaded == notes)
    }

    @Test
    fun `SHOULD edit note WHEN it exists`() {
        val noteBeforeUpdate = sut.findAll().first()
        val newTitle = noteBeforeUpdate.title + "update"
        val newContent = noteBeforeUpdate.content + "update"

        val entitiesUpdated = sut.updateNote(noteBeforeUpdate.id, newTitle, newContent)

        assert(sut.getById(noteBeforeUpdate.id).let {
            it.title == newTitle && it.content == newContent
        })
        assert(entitiesUpdated == 1)
    }

    @Test
    fun `SHOULD not update any entities WHEN note does not exist`() {
        val noteBeforeUpdate = sut.findAll().first()
        val newTitle = noteBeforeUpdate.title + "update"
        val newContent = noteBeforeUpdate.content + "update"

        val entitiesUpdated = sut.updateNote(EntityId.randomUUID(), newTitle, newContent)

        assert(entitiesUpdated == 0)
    }
}