package com.example.gotogether.domain.activity_log

data class ActivityLog(
    val userUuid: String,
    val device: String,
    val os: String,
    val publicIp: String,
    val entryDate: String
)
