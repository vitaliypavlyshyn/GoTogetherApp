package com.example.gotogether.data.activity_log

data class  ActivityLogRequestDTO(
    val userUuid: String,
    val device: String,
    val os: String,
    val publicIp: String,
    val entryDate: String
)
