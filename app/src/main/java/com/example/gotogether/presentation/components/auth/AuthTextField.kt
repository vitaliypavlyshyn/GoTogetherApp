package com.example.gotogether.presentation.components.auth

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80

@Composable
fun AuthTextField(
    input: MutableState<String>,
    inputFocusRequester: FocusRequester,
    //inputIsFocused: MutableState<Boolean>,
    authParameter: AuthParameter,
    modifier: Modifier,
    keyboardActions: KeyboardActions,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = input.value,
        onValueChange = {
            if (it.length <= authParameter.maxLength) {
                input.value = it
                onValueChange(it)
            }
        },
        placeholder = {
            Text(
                if (isFocused) authParameter.focusedPlaceholder
                else authParameter.unfocusedPlaceholder
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = PurpleGrey80.copy(alpha = 0.3f),
            unfocusedContainerColor = PurpleGrey80.copy(alpha = 0.3f),
            focusedBorderColor = Purple,
            unfocusedBorderColor = Purple
        ),
        modifier = modifier
            .focusRequester(inputFocusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = authParameter.imeAction),
        keyboardActions = keyboardActions
    )
}
