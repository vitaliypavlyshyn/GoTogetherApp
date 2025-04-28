package com.example.gotogether.presentation.screens.detailed_proposition_screen.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.domain.trip_passenger.TripPassenger
import com.example.gotogether.domain.trip_request.TripRequest
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import kotlinx.coroutines.launch

@Composable
fun RequestersColumn(
    requesters: List<TripRequest>,
    navController: NavController,
    onFirstClick: suspend (Long) -> Unit,
    onSecondClick: suspend (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxSize()) {
        repeat(requesters.size) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
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

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("user_profile/${requesters[index].passengerUuid}")
                        }
                ) {
                    Text(
                        text = requesters[index].passengerFirstName.orEmpty(),
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${requesters[index].requestedSeats} особа (-и)",
                        color = DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    if (requesters[index].phoneNumber != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Зателефонувати",
                            color = Purple,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = "tel:${requesters[index].phoneNumber}".toUri()
                                    }
                                    context.startActivity(intent)
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallIconButton(
                        onClick = { scope.launch { onFirstClick(requesters[index].requestId) } },
                        icon = Icons.Default.Check,
                        backgroundColor = Purple,
                        contentDescription = "Підтвердити"
                    )

                    SmallIconButton(
                        onClick = { scope.launch { onSecondClick(requesters[index].requestId) } },
                        icon = Icons.Default.Clear,
                        backgroundColor = DarkGreen,
                        contentDescription = "Відхилити"
                    )
                }
            }
        }
    }
}

@Composable
fun SmallIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    backgroundColor: Color,
    contentDescription: String,
) {
    Box(
        modifier = Modifier
            .size(32.dp) // невеликий розмір кнопки
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(18.dp) // невелика іконка всередині
        )
    }
}