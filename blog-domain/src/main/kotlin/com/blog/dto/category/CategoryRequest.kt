package com.blog.dto.category

import com.blog.domain.category.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateCategoryRequest(
    @field:NotBlank
    var categoryName: String
) {
    fun toEntity(): Category {
        return Category(categoryName)
    }
}

data class UpdateCategoryRequest(
    @field:NotNull
    var id: Long,
    @field:NotBlank
    var categoryName: String
)