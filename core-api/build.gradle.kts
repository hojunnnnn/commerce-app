plugins {
    alias(libs.plugins.epage.restdocs)
}
dependencies {
    runtimeOnly(project(":core-infra:jpa"))
    runtimeOnly(project(":core-infra:auth"))

    implementation(project(":core-app"))
    implementation(project(":core-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation(rootProject.libs.gson)

    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation(rootProject.libs.epage.restdocs.mockmvc)
    testImplementation(project(":core-infra:jpa"))
    testImplementation(testFixtures(project(":core-infra:jpa")))

}

openapi3 {
    this.setServer("http://localhost:8082/")
    title = "E-Commerce API"
    description = "E-Commerce API Documentation"
    version = "1.0.0"
    format = "yaml"
    outputDirectory = "src/main/resources/static/docs"
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}
