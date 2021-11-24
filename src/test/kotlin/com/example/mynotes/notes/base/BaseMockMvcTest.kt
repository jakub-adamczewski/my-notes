package com.example.mynotes.notes.base

import com.example.mynotes.auth.user.CurrentUser
import com.example.mynotes.config.test.ObjectMapperConfig
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.web.filter.OncePerRequestFilter

@Import(value = [ObjectMapperConfig::class, BaseMockMvcTest.TestConfig::class])
abstract class BaseMockMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var currentUser: CurrentUser

    @TestConfiguration
    class TestConfig {
        @Bean
        fun currentUser(): CurrentUser = SecurityTestData.currentUser

        @Bean
        @Qualifier("bearer_filter")
        fun bearerFilter(): OncePerRequestFilter = SecurityTestData.bearerFilter
    }

    protected fun MockHttpServletRequestBuilder.jsonBody(body: String): MockHttpServletRequestBuilder =
        content(body).contentType("application/json")

    protected fun MockHttpServletRequestBuilder.jsonBody(body: Any): MockHttpServletRequestBuilder =
        jsonBody(gson.toJson(body).toString())

    companion object {
        protected val gson = Gson()
    }
}