package com.blog.controller

import com.blog.ApiDocumentUtils
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class TestController(
    @Autowired
    private var mockMvc: MockMvc,

    @Autowired
    private val objectMapper: ObjectMapper,
) {

    @Test
    fun test() {
        // when
        val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/ping")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                document(
                    "test",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    responseFields(
                        fieldWithPath("data").description("ok"),
                        fieldWithPath("code").description("code")
                    )
                )
            )

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
    }
}