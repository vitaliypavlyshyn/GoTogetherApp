package com.example.gotogether.domain

enum class ReviewRating(val type: String, val rating: Int) {
    PERFECTLY("Чудово", 5),
    GOOD("Добре", 4),
    OK("Нормально", 3),
    EXPECTATIONS_NOT_MET("Очікування не виправдались", 2),
    VERY_DISAPPOINTED("Дуже розчарований(а)", 1)
}