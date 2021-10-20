package com.example.mynotes.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["api/test"])
class TestController(private val testRepository: TestRepository) {

    @GetMapping("fake")
    fun getFakeData(): List<Test> = listOf(
            Test(1, "Fake1"),
            Test(2, "Fake2"),
            Test(3, "Fake3"),
    )

    @GetMapping("db")
    fun getDbData(): List<Test> = testRepository.findAll()
}