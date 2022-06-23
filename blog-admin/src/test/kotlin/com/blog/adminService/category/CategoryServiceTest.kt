package com.blog.adminService.category

import com.blog.service.category.CategoryService
import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import com.blog.dto.category.CreateCategoryRequest
import com.blog.dto.category.UpdateCategoryRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CategoryServiceTest(
    @Autowired
    private val categoryService: CategoryService,

    @Autowired
    private val categoryRepository: CategoryRepository,
) {

    @AfterEach
    fun cleanUp() {
        categoryRepository.deleteAll()
    }

    @Test
    fun createCategory() {
        // given
        val request = CreateCategoryRequest("코틀린")

        // when
        categoryService.createCategory(request)

        // then
        val categoryList = categoryRepository.findAll()
        assertThat(categoryList[0].categoryName).isEqualTo(request.categoryName)
    }

    @Test
    fun updateCategory() {
        // given
        val category = categoryRepository.save(Category("코틀린"))

        val request = UpdateCategoryRequest(category.id, "코틀린 수정")

        // when
        categoryService.updateCategory(request)

        // then
        val categoryList = categoryRepository.findAll()
        assertThat(categoryList[0].categoryName).isEqualTo(request.categoryName)
    }

    @Test
    fun retrieveCategory() {
        // given
        val category1 = Category("코틀린1")
        val category2 = Category("코틀린2")
        val category3 = Category("코틀린3")
        categoryRepository.saveAll(listOf(category1, category2, category3))

        // when
        val retrieveCategory = categoryService.retrieveCategory()

        // then
        assertThat(retrieveCategory[0].categoryName).isEqualTo(category3.categoryName)
        assertThat(retrieveCategory[1].categoryName).isEqualTo(category2.categoryName)
        assertThat(retrieveCategory[2].categoryName).isEqualTo(category1.categoryName)
    }

}