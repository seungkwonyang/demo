package com.example.demo.batch.config

import com.example.demo.batch.job.RedisBatchJob
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RedisConfig::class.java)
    }
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory("localhost", 6379)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(redisConnectionFactory)

        // ObjectMapper 설정
        val objectMapper = ObjectMapper().apply {
            registerKotlinModule() // Kotlin 지원
            deactivateDefaultTyping()
        }

        // Serializer 설정
        val jsonSerializer = GenericJackson2JsonRedisSerializer(objectMapper)

        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = jsonSerializer
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = jsonSerializer

        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}



