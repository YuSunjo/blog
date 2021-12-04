package com.blog.dto.category

import com.blog.domain.category.Category
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

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