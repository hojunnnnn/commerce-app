package extensions

import kotlin.text.replace


private val CAMEL_REGEX = "(?<=[a-zA-Z])[A-Z]".toRegex()
private val SNAKE_REGEX = "_[a-zA-Z]".toRegex()

fun String.camelToSnakeCase(): String {
    return CAMEL_REGEX.replace(this) {
        "_${it.value}"
    }.lowercase()
}

fun String.snakeToCamelCase(): String {
    return SNAKE_REGEX.replace(this) {
        it.value.replace("_", "").uppercase()
    }
}