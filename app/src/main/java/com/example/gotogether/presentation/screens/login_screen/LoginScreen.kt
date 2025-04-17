package com.example.gotogether.presentation.screens.login_screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.presentation.components.auth.AuthFieldError
import com.example.gotogether.presentation.components.auth.AuthParameter
import com.example.gotogether.presentation.components.auth.AuthTextField
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.validation.AuthValidator

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var wasEmailTouched by remember { mutableStateOf(false) }
    var wasPasswordTouched by remember { mutableStateOf(false) }

    val loginState by loginViewModel.state.collectAsState()

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val formValid = emailError == null && passwordError == null

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
            modifier = Modifier
                .width(280.dp)
                .height(140.dp)
        )

        Column {
            Text(
                text = "Email",
                fontWeight = FontWeight.Bold
            )
            AuthTextField(
                input = email,
                inputFocusRequester = emailFocusRequester,
                authParameter = AuthParameter.EMAIL,
                modifier = Modifier.padding(top = 5.dp),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                ),
                onValueChange = {
                    email.value = it
                    wasEmailTouched = true
                    emailError = if (wasEmailTouched) {
                        AuthValidator.validateEmail(email.value)
                    } else {
                        null
                    }
                }
            )
            AuthFieldError(
                inputError = emailError,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(14.dp))

        Column {
            Text(
                text = "Пароль",
                fontWeight = FontWeight.Bold
            )
            AuthTextField(
                input = password,
                inputFocusRequester = passwordFocusRequester,
                authParameter = AuthParameter.PASSWORD,
                modifier = Modifier.padding(top = 5.dp),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                onValueChange = {
                    password.value = it
                    wasPasswordTouched = true
                    passwordError = if (wasPasswordTouched) {
                        AuthValidator.validatePassword(password.value)
                    } else {
                        null
                    }
                }
            )
            AuthFieldError(
                inputError = passwordError,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { loginViewModel.login(email.value, password.value) },
            enabled = formValid,
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            modifier = Modifier.width(100.dp)
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

        LaunchedEffect(loginState) {
            loginState?.let { result ->
                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()

                if (result.success) {
                    navController.navigate("profile") {
                        popUpTo("login") { inclusive = true }
                    }
                }

                loginViewModel.clearLoginState()
            }
        }
    }
}
