//package com.example.mynotes.user.db
//
//import com.example.mynotes.notes.TestDataStore.getUsers
//import com.ninjasquad.springmockk.MockkBean
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.runner.RunWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.test.context.junit4.SpringRunner
//
//@DataJpaTest
//@RunWith(SpringRunner::class)
//internal class UserRepositoryTest {
//
//    @Autowired
//    lateinit var sut: UserRepository
//
//    private val passwordEncoder = BCryptPasswordEncoder()
//    private val users = getUsers(passwordEncoder)
//
//    @BeforeEach
//    fun setUp() {
//        sut.saveAll(users)
//    }
//
//    @AfterEach
//    fun tearDown() {
//        sut.deleteAll()
//    }
//
//    @Test
//    fun `SHOULD return true WHEN user exists by email`() {
//        val existingEmail = users.first().email
//
//        assert(sut.existsByEmail(existingEmail))
//    }
//
//    @Test
//    fun `SHOULD return proper user WHEN filtered by email`() {
//        val testUser = users.first()
//
//        assert(sut.findByEmail(testUser.email) == testUser)
//    }
//
//    @Test
//    fun `SHOULD return null WHEN user with passed email not exists`() {
//        val wrongEmail = "bleble@wp.pl"
//
//        assert(wrongEmail !in users.map { it.email })
//        assert(sut.findByEmail(wrongEmail) == null)
//    }
//}