package com.example.demo.serving.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Any> {
        // ObjectMapper 설정
        val objectMapper: ObjectMapper = jacksonObjectMapper()
            .registerKotlinModule()

        // GenericJackson2JsonRedisSerializer를 사용
//        val jacksonSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
        val valueSerializer = Jackson2JsonRedisSerializer(Any::class.java)

        // RedisSerializationContext 설정
        val serializationContext = RedisSerializationContext.newSerializationContext<String, Any>(StringRedisSerializer())
            .value(valueSerializer)
            .hashKey(StringRedisSerializer())
            .hashValue(valueSerializer)
            .build()

        return ReactiveRedisTemplate(factory, serializationContext)
    }
}

