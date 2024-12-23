package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableRedisRepositories(basePackages = ["com.example.batch.redis"])
class BrandBatchJobApplication

fun main(args: Array<String>) {
	runApplication<BrandBatchJobApplication>(*args)
}
