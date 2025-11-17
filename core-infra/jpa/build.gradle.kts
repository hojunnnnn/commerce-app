plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "2.1.20"
}
dependencies {
    implementation(project(":core-domain"))
    implementation(project(":core-app"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}