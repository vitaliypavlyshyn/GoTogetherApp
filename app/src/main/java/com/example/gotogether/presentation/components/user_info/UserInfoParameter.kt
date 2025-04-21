package com.example.gotogether.presentation.components.user_info

import androidx.compose.ui.text.input.ImeAction

enum class UserInfoParameter(
    val focusedPlaceholder: String,
    val unfocusedPlaceholder: String,
    val maxLength: Int,
    val imeAction: ImeAction,
) {
    FIRST_NAME(
        focusedPlaceholder = "Від 2 до 30 символів",
        unfocusedPlaceholder = "Введіть ім'я",
        maxLength = 30,
        imeAction = ImeAction.Next),
    LAST_NAME(
        focusedPlaceholder = "Від 2 до 30 символів",
        unfocusedPlaceholder = "Введіть прізвище",
        maxLength = 30,
        imeAction = ImeAction.Next),
    PHONE_NUMBER(
        focusedPlaceholder = "Номер складається з 10 цифр",
        unfocusedPlaceholder = "Додайте номер телефону",
        maxLength = 10,
        imeAction = ImeAction.Next),
    DESCRIPTION(
        focusedPlaceholder = "Від 2 до 150 символів",
        unfocusedPlaceholder = "Додайте опис",
        maxLength = 150,
        imeAction = ImeAction.Done)
}