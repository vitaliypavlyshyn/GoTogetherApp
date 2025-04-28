package com.example.gotogether.domain.trip_request

enum class TripRequestStatus(val status: String) {
    PENDING("Очікується"),
    ACCEPTED("Підтверджено"),
    DECLINED("Відхилено"),
    CANCELED("Скасовано")
}