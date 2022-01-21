package com.blog.service.category

import com.blog.domain.category.repository.CategoryRepository
import com.blog.dto.category.CategoryInfoResponse
import com.blog.dto.category.CreateCategoryRequest
import com.blog.dto.category.UpdateCategoryRequest
import com.blog.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors

@Service
class CategoryService(
        private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun createCategory(request: CreateCategoryRequest): CategoryInfoResponse {
        return CategoryInfoResponse.of(categoryRepository.save(request.toEntity()))
    }

    @Transactional
    fun updateCategory(request: UpdateCategoryRequest): CategoryInfoResponse {
        val category = (categoryRepository.findCategoryById(request.id)
                ?: throw NotFoundException("존재하지 않는 카테고리 ${request.id} 입니다."))
        category.update(request.categoryName)
        return CategoryInfoResponse.of(category)
    }

    @Transactional(readOnly = true)
    fun retrieveCategory(): List<CategoryInfoResponse> {
        return categoryRepository.findCategory()
                .stream().map {
                    CategoryInfoResponse.of(it)
                }.collect(Collectors.toList())
    }

}