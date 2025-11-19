dependencies {
    runtimeOnly(project(":core-infra:jpa"))
    runtimeOnly(project(":core-infra:auth"))

    implementation(project(":core-app"))
    implementation(project(":core-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("com.google.code.gson:gson:2.13.0")
}