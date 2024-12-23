package com.example.demo.serving

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableRedisRepositories(basePackages = ["com.example.batch.redis"])
class BrandServingApiApplication

fun main(args: Array<String>) {
	runApplication<BrandServingApiApplication>(*args)
}
