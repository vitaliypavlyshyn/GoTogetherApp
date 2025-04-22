package com.example.gotogether.presentation.screens.profile_screen

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.presentation.components.verified_fields.UnverifiedField
import com.example.gotogether.presentation.components.verified_fields.VerifiedField
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.extentions.roundTo2DecimalPlaces


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val profileState = profileViewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        profileViewModel.loadUser()
    }
    var selectedImageByteArray by remember { mutableStateOf<ByteArray?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val byteArray = inputStream?.readBytes()
            selectedImageByteArray = byteArray

            byteArray?.let { photoBytes ->
                profileState.value.user?.getOrNull()?.let { user ->
                    profileViewModel.updateProfilePhoto(user.userUuid, photoBytes) {
                        profileViewModel.loadUser()
                    }
                }
            }
        }
    }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (profileState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            profileState.value.user?.onSuccess { user ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 50.dp)
                            .clickable(
                                onClick = {
                                    navController.navigate("user_profile/${user.userUuid}")
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(
                                        width = 3.dp,
                                        color = DarkGreen,
                                        shape = CircleShape
                                    )
                                    .padding(4.dp)
                                    .clip(CircleShape)
                            ) {
                                val bitmap = user.pictureProfile?.let {
                                    BitmapFactory.decodeByteArray(it, 0, it.size)
                                }?.asImageBitmap()

                                Image(
                                    bitmap = bitmap
                                        ?: ImageBitmap.imageResource(id = R.drawable.test_avatar),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = user.firstName,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkGray
                                )
                                if (user.avgRating != null) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "star",
                                            tint = Purple
                                        )
                                        Text(
                                            text = user.avgRating.roundTo2DecimalPlaces()
                                                .toString(),
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = DarkGray,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "Відгуків немає",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "right",
                            tint = DarkGray
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
                    ) {
                        Text(
                            text = "Змінити фото профілю",
                            color = DarkGreen,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        imagePickerLauncher.launch("image/*")
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Редагувати інформацію про себе",
                            color = DarkGreen,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        navController.navigate("change_info")
                                    }
                                )
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        thickness = 2.dp,
                        color = PurpleGrey80
                    )
                    if (user.phoneNumber != null) {
                        Text(
                            text = "Ваш профіль верифіковано",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        VerifiedField(
                            text = "monkeysigma208@gmail.com",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        VerifiedField(
                            text = "+38${user.phoneNumber}",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    } else {
                        Text(
                            text = "Ваш профіль не верифіковано",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        VerifiedField(
                            text = user.email ?: "",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        UnverifiedField(
                            text = "Номер телефону не вказано",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        thickness = 2.dp,
                        color = PurpleGrey80
                    )
                    Text(
                        text = "Про себе",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = MediumGray,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (user.description != null) {
                        Text(
                            text = user.description,
                            color = MediumGray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    } else {
                        Text(
                            text = "Інформація відсутня",
                            color = Color.Gray,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        thickness = 2.dp,
                        color = PurpleGrey80
                    )
                    Text(
                        text = "Транспортний засіб",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkGray,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    navController.navigate("choose_car")
                                }
                            )
                    ) {
                        if (user.carId == null) {
                            Icon(
                                imageVector = Icons.Default.AddCircleOutline,
                                contentDescription = "add_car",
                                tint = DarkGreen
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Додати авто",
                                color = DarkGreen,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = "car_icon",
                                tint = Purple
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${user.make?.uppercase()} ${user.model}",
                                color = Purple,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        thickness = 2.dp,
                        color = PurpleGrey80
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .clickable(
                                onClick = {

                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable(
                                onClick = {
                                    navController.navigate("settings/${user.userUuid}")
                                }
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "settings",
                                tint = MediumGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Налаштування",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MediumGray
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "right",
                                tint = MediumGray
                            )
                        }

                    }
                }
            }?.onFailure {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Помилка завантаження користувача")
                }
            }
        }
    }
}