package com.blog.domain.category

import com.blog.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class Category(
    var categoryName: String = "",
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    fun update(categoryName: String) {
        this.categoryName = categoryName
    }
}