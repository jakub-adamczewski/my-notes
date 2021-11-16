package com.example.mynotes.test

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController(private val testRepository: TestRepository) {

    @GetMapping("/fake")
    fun getFakeData(): List<Test> = listOf(
            Test(1, "Fake1"),
            Test(2, "Fake2"),
            Test(3, "Fake3"),
    )

    @GetMapping("/db")
    fun getDbData(): List<Test> = testRepository.findAll()

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/db", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postTest(@RequestBody createTestRequest: CreateTestRequest) {
        testRepository.save(Test(title = createTestRequest.title))
    }
}