package com.blog.service.category

import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class CategoryServiceTest(
    private val categoryRepository: CategoryRepository,
    private val categoryService: CategoryService
) {

    @DisplayName("카테고리 리스트")
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
        Assertions.assertThat(retrieveCategory[0].categoryName).isEqualTo(category3.categoryName)
        Assertions.assertThat(retrieveCategory[1].categoryName).isEqualTo(category2.categoryName)
        Assertions.assertThat(retrieveCategory[2].categoryName).isEqualTo(category1.categoryName)
    }

}