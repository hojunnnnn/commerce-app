rootProject.name = "ecommerce"

include(
    // inbound - web
    "core-api",

    // application
    "core-app",

    // domain
    "core-domain",

    // outbound - infra
    "core-infra:auth",
    "core-infra:jpa",

    // common
    "core-common"
)