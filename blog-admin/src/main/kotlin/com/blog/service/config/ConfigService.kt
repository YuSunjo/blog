package com.blog.service.config

import com.blog.domain.config.Config
import com.blog.domain.config.repository.ConfigRepository
import com.blog.dto.config.CreateAndUpdateConfigRequest
import com.blog.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConfigService(
    private val configRepository: ConfigRepository
) {
    @Transactional
    fun createConfig(request: CreateAndUpdateConfigRequest): Config {
        return configRepository.save(request.toEntity())
    }

    @Transactional
    fun updateConfig(request: CreateAndUpdateConfigRequest): Config {
        val config = configRepository.findById(1L)
            .orElseThrow {
                throw NotFoundException("존재하지 않는 설정입니다.");
            }
        config.update(request.backgroundImage)
        return config
    }

}