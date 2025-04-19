package com.example.gotogether.domain

enum class TripStatus(val status: String) {
    SCHEDULED("Заплановано"),
    IN_PROGRESS("Відбувається"),
    COMPLETED("Завершено"),
    CANCELED("Скасовано")
}