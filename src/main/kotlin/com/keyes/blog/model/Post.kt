package com.keyes.blog.model

import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @NotBlank
    var title: String,
    @Lob
    @NotEmpty
    var content: String,
    var createdOn: Instant,
    var updatedOn: Instant,
    @NotBlank
    var username: String,
)
