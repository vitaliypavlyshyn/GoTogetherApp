package com.example.gotogether.validation

object AuthValidator {
    fun validateEmail(input: String): String? =
        when {
            input.isBlank() -> "Email не може бути порожнім"
            input.length > 30 -> "Email не має перевищувати 30 символів"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches() ->
                "Неправильний формат email"

            else -> null
        }

    fun validatePassword(input: String): String? {
        if (input.length !in 8..15) {
            return "Пароль має бути від 8 до 15 символів"
        }
        if (!input.any { it.isDigit() }) {
            return "Пароль має містити принаймні одну цифру"
        }
        if (!input.any { it.isLetter() }) {
            return "Пароль має містити принаймні одну літеру"
        }
        if (input.any { it.isWhitespace() }) {
            return "Пароль не повинен містити пробілів"
        }
        return null
    }
}