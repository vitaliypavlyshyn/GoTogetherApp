package com.example.gotogether.presentation.screens.validation_screens.change_info_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.data.user.UpdateUserRequestDTO

import com.example.gotogether.presentation.components.DatePickerFieldMaterial3
import com.example.gotogether.presentation.components.auth.FieldError
import com.example.gotogether.presentation.components.user_info.UserInfoParameter
import com.example.gotogether.presentation.components.user_info.UserInfoTextField
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.utils.validation.AuthValidator
import com.example.gotogether.utils.validation.UserValidator
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ChangeInfoScreen(
    changeInfoViewModel: ChangeInfoViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val userInfoState = changeInfoViewModel.state.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (userInfoState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            userInfoState.value.user?.onSuccess { userInfo ->
                val firstName = remember { mutableStateOf(userInfo.firstName) }
                val lastName = remember { mutableStateOf(userInfo.lastName) }
                val phoneNumber = remember { mutableStateOf(userInfo.phoneNumber ?: "") }
                val description = remember { mutableStateOf(userInfo.description ?: "") }
                var dateOfBirth = remember { mutableStateOf(userInfo.dateOfBirth) }

                val firstNameFocusRequester = remember { FocusRequester() }
                val lastNameFocusRequester = remember { FocusRequester() }
                val phoneNumberFocusRequester = remember { FocusRequester() }
                val descriptionFocusRequester = remember { FocusRequester() }

                var wasFirstNameTouched by remember { mutableStateOf(false) }
                var wasLastNameTouched by remember { mutableStateOf(false) }
                var wasPhoneNumberTouched by remember { mutableStateOf(false) }
                var wasDescriptionTouched by remember { mutableStateOf(false) }

                var firstNameError by remember { mutableStateOf<String?>(null) }
                var lastNameError by remember { mutableStateOf<String?>(null) }
                var phoneNumberError by remember { mutableStateOf<String?>(null) }
                var descriptionError by remember { mutableStateOf<String?>(null) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 80.dp)
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                                tint = Purple
                            )
                        }
                        Text(
                            text = "Змінити особисту інформацію",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MediumGray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Ім'я",
                                fontWeight = FontWeight.Medium,
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
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Прізвище",
                                fontWeight = FontWeight.Medium,
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
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Номер телефону",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = MediumGray
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            UserInfoTextField(
                                input = phoneNumber,
                                inputFocusRequester = phoneNumberFocusRequester,
                                userInfoParameter = UserInfoParameter.PHONE_NUMBER,
                                keyboardActions = KeyboardActions(
                                    onNext = { phoneNumberFocusRequester.requestFocus() }
                                ),
                                onValueChange = {
                                    phoneNumber.value = it
                                    wasPhoneNumberTouched = true
                                    phoneNumberError = if (wasPhoneNumberTouched) {
                                        UserValidator.validatePhoneNumber(phoneNumber.value)
                                    } else {
                                        null
                                    }
                                }
                            )
                            FieldError(
                                inputError = phoneNumberError,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        ) {
                            Text(
                                text = "Дата народження",
                                fontWeight = FontWeight.Medium,
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
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Опис про себе",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = MediumGray
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            UserInfoTextField(
                                input = description,
                                inputFocusRequester = descriptionFocusRequester,
                                userInfoParameter = UserInfoParameter.DESCRIPTION,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                singleLine = false,
                                keyboardActions = KeyboardActions(
                                    onNext = { descriptionFocusRequester.requestFocus() }
                                ),
                                onValueChange = {
                                    description.value = it
                                    wasDescriptionTouched = true
                                    descriptionError = if (wasDescriptionTouched) {
                                        UserValidator.validateDescription(description.value)
                                    } else {
                                        null
                                    }
                                }
                            )
                            FieldError(
                                inputError = descriptionError,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(90.dp))
                    }

                    Button(
                        onClick = {
                            changeInfoViewModel.updateUser(
                                userInfo.userUuid,
                                UpdateUserRequestDTO(
                                    firstName = firstName.value,
                                    lastName = lastName.value,
                                    description = description.value,
                                    dateOfBirth = dateOfBirth.value,
                                    phoneNumber = phoneNumber.value
                                )
                            )
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .height(60.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Підтвердити",
                                fontWeight = FontWeight.Medium,
                            )

                        }
                    }
                }
            }?.onFailure {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Помилка завантаження інформації про користувача")
                }
            }
        }
    }

}