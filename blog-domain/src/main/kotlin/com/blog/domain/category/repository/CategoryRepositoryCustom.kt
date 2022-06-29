package com.blog.domain.category.repository

import com.blog.domain.category.Category

interface CategoryRepositoryCustom {
    fun findCategoryById(id: Long): Category?

    fun findCategory(): List<Category>
}