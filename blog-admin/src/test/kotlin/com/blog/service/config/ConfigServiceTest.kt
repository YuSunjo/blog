package com.blog.service.config

import com.blog.domain.config.Config
import com.blog.domain.config.repository.ConfigRepository
import com.blog.dto.config.CreateAndUpdateConfigRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ConfigServiceTest(
    @Autowired
    private val configService: ConfigService,

    @Autowired
    private val configRepository: ConfigRepository,
) {

    @AfterEach
    fun cleanUp() {
        configRepository.deleteAll()
    }

    @Test
    fun createConfig() {
        // given
        val request = CreateAndUpdateConfigRequest("https://naver.com")

        // when
        configService.createConfig(request)

        // then
        val configList = configRepository.findAll()
        assertThat(configList).hasSize(1)
        assertThat(configList[0].backgroundImage).isEqualTo(request.backgroundImage)
    }

    @Test
    fun updateConfig() {
        // given
        configRepository.save(Config("https://naver.com"))
        val request = CreateAndUpdateConfigRequest("https://naver.com")

        // when
        configService.createConfig(request)

        // then
        val configList = configRepository.findAll()
        assertThat(configList).hasSize(1)
        assertThat(configList[0].backgroundImage).isEqualTo(request.backgroundImage)
    }

    @Test
    fun getConfig() {
        // given
        val config = Config("https://naver.com")
        configRepository.save(config)

        // when
        val response = configService.getConfig()

        // then
        assertThat(response?.backgroundImage).isEqualTo(config.backgroundImage)
    }

    @DisplayName("만약 config가 없다면 null 반환")
    @Test
    fun getConfig2() {
        // when
        val response = configService.getConfig()

        // then
        assertThat(response?.backgroundImage).isNull()
    }

}