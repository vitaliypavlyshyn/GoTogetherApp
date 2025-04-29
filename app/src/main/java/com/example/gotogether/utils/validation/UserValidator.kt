package com.example.gotogether.utils.validation

import android.util.Patterns

object UserValidator {
    private val validPhonePrefixes = listOf(
        "067", "068", "096", "097", "098", "077",
        "050", "066", "095", "099", "075",
        "063", "073", "093",
        "089", "094", "020"
    )

    private val nameRegex = Regex("^[А-ЯІЇЄа-яіїєA-Za-z]{2,}$")

    fun validateFirstName(input: String): String?  {
        if (!nameRegex.matches(input)) {
            return "Ім’я повинно містити лише літери."
        }
        if (input.length !in 2..30) {
            return "Ім’я повинно містити від 2 до 30 символів."
        }
        return null
    }

    fun validateLastName(input: String): String?  {
        if (!nameRegex.matches(input)) {
            return "Прізвище повинно містити лише літери."
        }
        if (input.length !in 2..30) {
            return "Прізвище повинно містити від 2 до 30 символів."
        }
        return null
    }

    fun validateDescription(input: String): String?  {
        if (input.isBlank()) return null
        if (input.length > 150) {
            return "Опис має бути до 150 символів."
        }
        return null
    }

    fun validateComment(input: String): String?  {
        if (input.isBlank()) return null
        if ((input.length > 150) && (input.length < 10)) {
            return "Опис має бути від 10 до 150 символів."
        }
        return null
    }

    fun validatePhoneNumber(input: String): String?  {
        if (input.isBlank()) return null
        if (input.length != 10) {
            return "Номер телефону повинен містити 10 цифр."
        } else if (validPhonePrefixes.none { input.startsWith(it) }) {
            return "Номер телефону повинен починатися з коректного коду оператора."
        } else if (!input.all { it.isDigit() }) {
            return  "Номер телефону повинен містити лише цифри."
        }
        return null
    }


}