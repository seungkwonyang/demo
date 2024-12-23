plugins {
	id("idea")
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.20"
	kotlin("plugin.spring") version "1.8.20"
	kotlin("plugin.jpa") version "1.8.20"
	id("io.freefair.lombok") version "8.1.0"
}

allprojects {
	group = "com.example"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	dependencies {
		implementation("org.slf4j:slf4j-api:2.0.7") // SLF4J API
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
		runtimeOnly("com.h2database:h2")

		implementation("org.slf4j:slf4j-api:2.0.7")
		implementation("ch.qos.logback:logback-classic:1.4.11") // Logback 사용
		// slf4j-simple 제거
		configurations.all {
			exclude(group = "org.slf4j", module = "slf4j-simple")
		}

		// Kotest dependencies
		testImplementation("io.kotest:kotest-runner-junit5:5.6.2") // Core runner
		testImplementation("io.kotest:kotest-assertions-core:5.6.2") // Assertions
		testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2") // Spring extension for Kotest
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testRuntimeOnly("com.h2database:h2")
		// Mockk for mocking
		testImplementation("io.mockk:mockk:1.13.5")

		// Testcontainers for integration tests
		testImplementation("org.testcontainers:junit-jupiter:1.19.0")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}
