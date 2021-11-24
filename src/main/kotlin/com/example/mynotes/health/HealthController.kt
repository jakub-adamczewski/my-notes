package com.example.mynotes.health

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping
    fun checkHealth() = "Api works."
}