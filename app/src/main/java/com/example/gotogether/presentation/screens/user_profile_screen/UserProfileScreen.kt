package com.example.gotogether.presentation.screens.user_profile_screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.presentation.screens.profile_screen.VerifiedField
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.extentions.roundTo2DecimalPlaces
import com.example.gotogether.utils.formatter.TimeFormatter

@Composable
fun UserProfileScreen(
    userState: UserProfileViewModel.UserState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (userState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            userState.user?.onSuccess { user ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .clickable(
                                onClick = {

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
                                Image(
                                    painter = painterResource(R.drawable.test_avatar),
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

                                Text(
                                    text = "${TimeFormatter.getUserAgeFromString(user.dateOfBirth)} років",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MediumGray,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp)
                    ) {
                        if (user.avgRating != null) {
                            Row(
                                modifier = Modifier.clickable(
                                    onClick = {
                                        navController.navigate("reviews/${user.userUuid}")
                                    }
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "star",
                                    tint = Purple
                                )
                                Text(
                                    text = "${user.avgRating.roundTo2DecimalPlaces()}/5 - ${user.countReviews} відгуків",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MediumGray,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "right",
                                    tint = MediumGray
                                )

                            }
                        } else {
                            Text(
                                text = "Відгуків немає",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkGray,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                    if (user.phoneNumber != null) {
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = PurpleGrey80,
                        modifier = Modifier.padding(16.dp)
                    )
                        VerifiedField(
                            text = "Підтверджений номер телефону",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        thickness = 8.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${user.firstName} про себе",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = MediumGray,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (user.description != null) {
                        Text(
                            text = user.description,
                            fontWeight = FontWeight.Medium,
                            color = MediumGray,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    } else {
                        Text(
                            text = "Інформація відсутня",
                            fontWeight = FontWeight.Medium,
                            color = MediumGray,
                            fontSize = 15.sp,
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

                                }
                            )
                    ) {
                        if(user.carId != null) {
                            Icon(
                                imageVector = Icons.Default.DirectionsCar,
                                contentDescription = "car_icon",
                                tint = MediumGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${user.make} ${user.model}",
                                color = MediumGray,
                                fontSize = 16.sp
                            )
                        } else {
                            Text(
                                text = "Інформація відсутня",
                                fontWeight = FontWeight.Medium,
                                color = MediumGray,
                                fontSize = 16.sp,
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
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                            .clickable(
                                onClick = {

                                }
                            )
                    ) {

                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "report",
                            tint = DarkGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Поскаржитися на профіль",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGreen
                        )
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