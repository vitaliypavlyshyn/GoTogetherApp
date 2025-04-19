package com.example.gotogether.presentation.screens.detailed_trip_screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsCarFilled
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotogether.R
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.Light
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80

@Composable
@Preview(showBackground = true)
fun DetailedTripScreenPreview(modifier: Modifier = Modifier) {
    val isFastConfirm = false
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val backgroundColor =
        if (isPressed.value) Color(0x1A004D40) else Color.Transparent // світло-зелений при натисканні
    val contentColor = if (isPressed.value) DarkGreen.copy(alpha = 0.6f) else DarkGreen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Залишаємо місце для кнопки
                .verticalScroll(rememberScrollState())
            //.padding(horizontal = 16.dp)
        ) {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = Purple
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "19 квітня",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MediumGray,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {

                        }
                    )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Text(
                            text = "10:40",
                            color = MediumGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "71 км",
                            color = MediumGray,
                            fontSize = 10.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "11:50",
                        color = MediumGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Text(
                            text = "Львів",
                            color = MediumGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Львіська область",
                            color = MediumGray,
                            fontSize = 10.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Column {
                        Text(
                            text = "Шептицький",
                            color = MediumGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Львіська область",
                            color = MediumGray,
                            fontSize = 10.sp,
                        )
                    }
                }
            }
            //Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                thickness = 10.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.padding(start = 18.dp, end = 18.dp).height(32.dp)) {
                Text(
                    text = "1 пасажир",
                    color = MediumGray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "100 ₴",
                    color = MediumGray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            HorizontalDivider(
                thickness = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable(
                        onClick = {

                        }
                    )
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .border(
                            width = 1.dp,
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
                Spacer(modifier = Modifier.width(8.dp))
                Column() {
                    Text(
                        text = "Maksiemen",
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "car",
                            tint = Purple,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "4.9 - 2 відгуки",
                            color = DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "right",
                    tint = DarkGray
                )
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = PurpleGrey80,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                if (!isFastConfirm) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "confirm_type",
                        tint = MediumGray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Бронювання буде підтверджено лише після того, як водій підтвердить запит",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MediumGray
                    )
                } else {
                    Icon(
                        Icons.Default.AutoMode,
                        contentDescription = "confirm_type",
                        tint = MediumGray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Броюнвання буде підтверджене одразу",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MediumGray
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "car_icon",
                    tint = MediumGray,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "DODGE JOURNEY",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MediumGray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* Дія */ },
                    interactionSource = interactionSource,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = backgroundColor,
                        contentColor = contentColor
                    ),
                    border = BorderStroke(1.dp, DarkGreen),
                    modifier = Modifier.height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "call",
                        tint = DarkGreen
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Подзвонити до Maksiemen",
                        color = DarkGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                thickness = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Пасажири",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = MediumGray,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable(
                        onClick = {

                        }
                    )
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)

                ) {
                    Image(
                        painter = painterResource(R.drawable.tuqui),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column() {
                    Text(
                        text = "Віталік",
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Львів → Шептицький (+1)",
                        color = DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "right",
                    tint = DarkGray
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                thickness = 10.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
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
                    text = "Поскаржитися на поїздку",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkGreen
                )
            }
            Spacer(modifier = Modifier.height(100.dp)) // Щоб останній елемент не "залипав" під кнопкою
        }

        // Кнопка знизу екрана
        Button(
            onClick = { /* дія */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .height(50.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isFastConfirm) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "confirm_type",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Надіслати запит",
                        fontWeight = FontWeight.Medium,
                    )
                } else {
                    Icon(
                        Icons.Default.AutoMode,
                        contentDescription = "confirm_type",
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Забронювати",
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}
