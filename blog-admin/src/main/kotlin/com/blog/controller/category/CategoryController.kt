package com.blog.controller.category

import com.blog.ApiResponse
import com.blog.config.auth.Member
import com.blog.dto.category.CategoryInfoResponse
import com.blog.dto.category.CreateCategoryRequest
import com.blog.dto.category.UpdateCategoryRequest
import com.blog.service.category.CategoryService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class CategoryController(
        private val categoryService: CategoryService
) {
    @Member
    @PostMapping("/api/v1/admin/category")
    fun createCategory(@RequestBody @Valid request: CreateCategoryRequest): ApiResponse<CategoryInfoResponse> {
        return ApiResponse.success(categoryService.createCategory(request))
    }

    @Member
    @PutMapping("/api/v1/admin/category")
    fun updateCategory(@RequestBody @Valid request: UpdateCategoryRequest): ApiResponse<CategoryInfoResponse> {
        return ApiResponse.success(categoryService.updateCategory(request))
    }

    @GetMapping("/api/v1/admin/category/list")
    fun retrieveCategory(): ApiResponse<List<CategoryInfoResponse>> {
        return ApiResponse.success(categoryService.retrieveCategory())
    }

}