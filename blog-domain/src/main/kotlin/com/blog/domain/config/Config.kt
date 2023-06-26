package com.blog.domain.config

import com.blog.domain.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Config(
    var backgroundImage: String = ""
) : BaseTimeEntity() {
    @Id
    var id: Long = 1L

    fun update(backgroundImage: String) {
        this.backgroundImage = backgroundImage
    }
}