plugins {
    id("com.epages.restdocs-api-spec") version "0.18.2"
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
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.2")

}