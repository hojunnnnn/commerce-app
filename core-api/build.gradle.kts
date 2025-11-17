dependencies {
    runtimeOnly(project(":core-infra:jpa")) // SpringBoot가 Bean을 스캔할 수 있도록

    implementation(project(":core-app"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("com.google.code.gson:gson:2.13.0")
}