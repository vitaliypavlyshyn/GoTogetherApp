package com.example.gotogether.presentation.components.user_info

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
fun UserInfoTextField(
    input: MutableState<String>,
    inputFocusRequester: FocusRequester,
    //inputIsFocused: MutableState<Boolean>,
    singleLine: Boolean = true,
    userInfoParameter: UserInfoParameter,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = input.value,
        onValueChange = {
            if (it.length <= userInfoParameter.maxLength) {
                input.value = it
                onValueChange(it)
            }
        },
        placeholder = {
            Text(
                if (isFocused) userInfoParameter.focusedPlaceholder
                else userInfoParameter.unfocusedPlaceholder,
            )
        },
        singleLine = singleLine,
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
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = userInfoParameter.imeAction),
        keyboardActions = keyboardActions
    )
}

