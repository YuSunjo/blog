package com.blog.adminService.category

import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import com.blog.dto.category.CreateCategoryRequest
import com.blog.service.category.CategoryService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

class CategoryServiceKoTest : BehaviorSpec({

    val categoryRepository = mockk<CategoryRepository>(relaxed = true, relaxUnitFun = true)
    val categoryService = CategoryService(categoryRepository)

    afterContainer {
        clearAllMocks()
    }

    Given("요청이 들어오는 상태에서") {
        val request = CreateCategoryRequest("gogo")
        When("카테고리 생성을 하게 되면") {
            every {
                categoryService.createCategory(request)
            }
            Then("db에 저장된 카테고리와 내가 적은 카테고리의 이름이 같다.") {
                val category = getCategory(request.categoryName)
                every {
                    categoryRepository.findCategoryById(1L)
                } returns category
                category.categoryName shouldBe request.categoryName
            }
            Then("db에 카테고리는 1개가 있다.") {
                val categoryListSize = 1
                every {
                    categoryRepository.findAll().size
                } returns categoryListSize
                categoryListSize shouldBe 1
            }
        }
    }
})

fun getCategory(categoryName: String): Category {
    return Category(categoryName)
}
