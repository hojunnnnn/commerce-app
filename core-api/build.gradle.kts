plugins {
    id("com.epages.restdocs-api-spec") version "0.19.4"
}
dependencies {
    runtimeOnly(project(":core-infra:jpa"))
    runtimeOnly(project(":core-infra:auth"))

    implementation(project(":core-app"))
    implementation(project(":core-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.google.code.gson:gson:2.13.0")


    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.4")
    testImplementation("org.springframework:spring-tx")
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
