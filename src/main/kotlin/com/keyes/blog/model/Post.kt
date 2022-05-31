package com.keyes.blog.model

import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @NotBlank
    val title: String,
    @Lob
    @NotEmpty
    val content: String,
    val createdOn: Instant,
    val updatedOn: Instant,
    @NotBlank
    val username: String,
)
