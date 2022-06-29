package com.blog.service.category

import com.blog.domain.category.repository.CategoryRepository
import com.blog.dto.category.CategoryInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    @Transactional(readOnly = true)
    fun retrieveCategory(): List<CategoryInfoResponse> {
        return categoryRepository.findCategory()
            .stream().map {
                CategoryInfoResponse.of(it)
            }.collect(Collectors.toList())
    }
}