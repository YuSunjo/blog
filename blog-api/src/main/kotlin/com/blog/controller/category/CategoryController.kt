package com.blog.controller.category

import com.blog.ApiResponse
import com.blog.dto.category.CategoryInfoResponse
import com.blog.service.category.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping("/api/v1/category/list")
    fun retrieveCategory(): ApiResponse<List<CategoryInfoResponse>> {
        return ApiResponse.success(categoryService.retrieveCategory())
    }

}