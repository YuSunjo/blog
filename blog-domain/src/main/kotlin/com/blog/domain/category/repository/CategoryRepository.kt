package com.blog.domain.category.repository

import com.blog.domain.category.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long>, CategoryRepositoryCustom {
}