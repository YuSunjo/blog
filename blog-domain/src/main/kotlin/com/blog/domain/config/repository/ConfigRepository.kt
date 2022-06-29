package com.blog.domain.config.repository

import com.blog.domain.config.Config
import org.springframework.data.jpa.repository.JpaRepository

interface ConfigRepository : JpaRepository<Config, Long>, ConfigRepositoryCustom