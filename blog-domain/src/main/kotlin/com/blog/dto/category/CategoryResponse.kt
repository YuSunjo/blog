package com.blog.dto.category

import com.blog.domain.category.Category

data class CategoryInfoResponse(
    var id: Long,
    var categoryName: String
) {
    companion object {
        fun of(category: Category): CategoryInfoResponse {
            return CategoryInfoResponse(category.id, category.categoryName)
        }
    }
}