package com.example.mynotes.test

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
data class Test(
        @Id @GeneratedValue var id: Long? = null,
        var title: String
)
