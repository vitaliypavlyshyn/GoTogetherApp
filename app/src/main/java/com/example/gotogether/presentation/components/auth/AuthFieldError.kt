package com.example.gotogether.presentation.components.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FieldError(
    inputError: String?,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.height(16.dp)) {
        AnimatedVisibility(visible = inputError != null) {
            if (inputError != null) {
                Text(
                    text = inputError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = modifier
                )
            }
        }
    }
}