package com.example.gotogether.domain.user

enum class DrivingSkillsRating(val type: String, val rating: Int?) {
    GOOD("Добре", 3),
    MEDIUM("Звичайно", 2),
    BAD("Погано", 1),
    SKIP("Пропустити", null)
}