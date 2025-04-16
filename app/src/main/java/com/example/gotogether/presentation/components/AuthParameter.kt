package com.example.gotogether.presentation.components

import androidx.compose.ui.text.input.ImeAction

enum class AuthParameter(
    val focusedPlaceholder: String,
    val unfocusedPlaceholder: String,
    val maxLength: Int,
    val imeAction: ImeAction,
) {
    EMAIL(
        focusedPlaceholder = "Введіть свій email тут...",
        unfocusedPlaceholder = "Введіть email",
        maxLength = 30,
        imeAction = ImeAction.Next),
    PASSWORD(
        focusedPlaceholder = "Від 8 до 15 символів",
        unfocusedPlaceholder = "Введіть пароль",
        maxLength = 15,
        imeAction = ImeAction.Done)
}