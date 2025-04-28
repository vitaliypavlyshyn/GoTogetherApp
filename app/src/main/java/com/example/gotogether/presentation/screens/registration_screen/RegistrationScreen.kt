package com.example.gotogether.presentation.screens.registration_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.gotogether.presentation.components.DatePickerFieldMaterial3
import com.example.gotogether.presentation.components.auth.AuthParameter
import com.example.gotogether.presentation.components.auth.AuthTextField
import com.example.gotogether.presentation.components.auth.FieldError
import com.example.gotogether.presentation.components.user_info.UserInfoParameter
import com.example.gotogether.presentation.components.user_info.UserInfoTextField
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.utils.validation.AuthValidator
import com.example.gotogether.utils.validation.UserValidator
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = hiltViewModel<RegistrationViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    var dateOfBirth = remember { mutableStateOf("2000-01-01") }

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }

    var wasFirstNameTouched by remember { mutableStateOf(false) }
    var wasLastNameTouched by remember { mutableStateOf(false) }
    var wasEmailTouched by remember { mutableStateOf(false) }
    var wasPasswordTouched by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val registrationState by registrationViewModel.state.collectAsState()

    var firstNameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
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
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Ім'я",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MediumGray
            )
            Spacer(modifier = Modifier.height(5.dp))
            UserInfoTextField(
                input = firstName,
                inputFocusRequester = firstNameFocusRequester,
                userInfoParameter = UserInfoParameter.FIRST_NAME,
                keyboardActions = KeyboardActions(
                    onNext = { lastNameFocusRequester.requestFocus() }
                ),
                onValueChange = {
                    firstName.value = it
                    wasFirstNameTouched = true
                    firstNameError = if (wasFirstNameTouched) {
                        UserValidator.validateFirstName(firstName.value)
                    } else {
                        null
                    }
                }
            )
            FieldError(
                inputError = firstNameError,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Прізвище",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MediumGray
            )
            Spacer(modifier = Modifier.height(5.dp))
            UserInfoTextField(
                input = lastName,
                inputFocusRequester = lastNameFocusRequester,
                userInfoParameter = UserInfoParameter.LAST_NAME,
                keyboardActions = KeyboardActions(
                    onNext = { lastNameFocusRequester.requestFocus() }
                ),
                onValueChange = {
                    lastName.value = it
                    wasLastNameTouched = true
                    lastNameError = if (wasLastNameTouched) {
                        UserValidator.validateLastName(lastName.value)
                    } else {
                        null
                    }
                }
            )
            FieldError(
                inputError = lastNameError,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Дата народження",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MediumGray
            )
            Spacer(modifier = Modifier.height(5.dp))
            DatePickerFieldMaterial3(
                selectedDate = dateOfBirth.value
                    ?.let { LocalDate.parse(it).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) }
                    ?: "",
                onDateSelected = {
                    dateOfBirth.value = it.format(DateTimeFormatter.ISO_LOCAL_DATE)
                },
                modifier = Modifier.width(280.dp)
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Column {
            Text(
                text = "Email",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MediumGray
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
            FieldError(
                inputError = emailError,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Column {
            Text(
                text = "Пароль",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MediumGray
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
            FieldError(
                inputError = passwordError,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                registrationViewModel.register(
                    email = email.value,
                    password = password.value,
                    firstName = firstName.value,
                    lastName = lastName.value,
                    dateOfBirth = dateOfBirth.value
                )
            },
            enabled = formValid,
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            modifier = Modifier.height(45.dp)
        ) {
            Text(text = "Зареєструватись")
        }
        Spacer(modifier = Modifier.height(5.dp))

        Row {
            Text(
                text = "Уже є обліковий запис? ",
                fontSize = 13.sp
            )
            Text(
                text = "Увійти",
                fontSize = 13.sp,
                color = Purple,
                modifier = Modifier.clickable(
                    onClick = {
                        navController.navigate("login")
                    }
                ))

        }

        LaunchedEffect(registrationState) {
            registrationState?.onSuccess { res ->
                if (res.isSuccess) {
                    navController.navigate("login") {
                        popUpTo("registration") { inclusive = true }
                    }
                }
                Toast.makeText(context, res.message, Toast.LENGTH_LONG).show()
            }?.onFailure { res ->
                Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
            }
            registrationViewModel.clearRegistrationState()
        }
    }
}