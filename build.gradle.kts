plugins {
    id("idea")
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.20"
	kotlin("plugin.spring") version "1.8.20"
	kotlin("plugin.jpa") version "1.8.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	runtimeOnly("com.h2database:h2")

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
	testLogging {
		events("PASSED", "FAILED", "SKIPPED")
		exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
	}
}
