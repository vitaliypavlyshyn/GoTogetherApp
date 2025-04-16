package com.example.gotogether.presentation.screens.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.presentation.components.AuthParameter
import com.example.gotogether.presentation.components.AuthTextField
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.Purple

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val emailIsFocused = remember { mutableStateOf(false) }
    val passwordIsFocused = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = "background",
                modifier = Modifier.width(280.dp).height(140.dp)
            )

            Column {
                Text(
                    text = "Email",
                    fontWeight = FontWeight.Bold
                )
                AuthTextField(
                    input = email,
                    inputIsFocused = emailIsFocused,
                    inputFocusRequester = emailFocusRequester,
                    authParameter = AuthParameter.EMAIL,
                    modifier = Modifier.padding(top = 5.dp),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequester.requestFocus() }
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Text(
                    text = "Пароль",
                    fontWeight = FontWeight.Bold
                )
                AuthTextField(
                    input = password,
                    inputIsFocused = passwordIsFocused,
                    inputFocusRequester = passwordFocusRequester,
                    authParameter = AuthParameter.PASSWORD,
                    modifier = Modifier.padding(top = 5.dp),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            ) {
                Text(text = "Увійти")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = "Немає облікового запису? ",
                    fontSize = 13.sp
                )
                Text(
                    text = "Зареєструватися",
                    fontSize = 13.sp,
                    color = Purple,
                    modifier = Modifier.clickable(
                        onClick = {
                            navController.navigate("registration")
                        }
                    ))

        }
    }
}
