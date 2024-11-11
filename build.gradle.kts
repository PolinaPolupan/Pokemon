plugins {
    java
    application
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.autonomousapps.dependency-analysis") version "2.4.2"
}

group = "ru.mirea"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    // Spring Boot
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.2.4.RELEASE"))

    // Jackson & Retrofit
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.11.0")

    // Database
    implementation("org.postgresql:postgresql")

    // Utilities
    implementation("com.google.guava:guava:20.0")
    compileOnly("org.jetbrains:annotations:24.1.0")
    testImplementation("junit:junit")

    // Annotation Processing
    compileOnly("org.projectlombok:lombok")
  implementation("com.squareup.okhttp3:okhttp:4.12.0")
  implementation("org.springframework:spring-beans:6.1.14")
  implementation("org.springframework.boot:spring-boot-autoconfigure:3.3.5")
  implementation("org.springframework:spring-context:6.1.14")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.5")
  implementation("org.springframework.boot:spring-boot:3.3.5")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

application {
    mainClass.set("ru.mirea.pkmn.PkmnApplication")
}
