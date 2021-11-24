package com.example.mynotes.config.test

import com.fasterxml.jackson.databind.Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.problem.jackson.ProblemModule
import org.zalando.problem.violations.ConstraintViolationProblemModule

@Configuration
class ObjectMapperConfig {
    @Bean
    fun problemModule(): Module {
        return ProblemModule()
    }

    @Bean
    fun constraintViolationProblemModule(): Module {
        return ConstraintViolationProblemModule()
    }
}