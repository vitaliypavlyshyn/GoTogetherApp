package com.example.gotogether.presentation.screens.my_propositions_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AirlineSeatReclineExtra
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DirectionsCarFilled
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.domain.ChosenRoute
import com.example.gotogether.domain.TripStatus
import com.example.gotogether.domain.trip.Trip
import com.example.gotogether.presentation.screens.my_propositions_screen.MyPropositionsViewModel.UserPreview
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.DarkGreen
import com.example.gotogether.ui.theme.DarkWhite
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.extentions.roundTo2DecimalPlaces
import com.example.gotogether.utils.formatter.TimeFormatter

@Composable
fun MyPropositionCard(
    navController: NavController,
    trip: Trip,
    reviewableUsersMap: Map<Long, List<UserPreview>>,
    modifier: Modifier = Modifier,
) {
    val typeConfirmIcon = remember {
        mutableStateOf(checkTypeConfirmIcon(trip.isFastConfirm))
    }
    val reviewableUsers = reviewableUsersMap[trip.tripId].orEmpty()
    if (reviewableUsers.isNotEmpty() &&
        trip.status != TripStatus.SCHEDULED.status &&
        trip.status != TripStatus.CANCELED.status
        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clickable(
                    onClick = {
                        navController.navigate("reviewable_list_trip/${trip.tripId}")
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.StarBorder,
                contentDescription = "rating",
                tint = Purple
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Залишити відгук",
                color = Purple,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Card(
        modifier = Modifier
            .width(400.dp)
           // .height(160.dp)
            .clickable(
                onClick = {
                     navController.navigate("detailed_proposition/${trip.tripId}")
                }
            ),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, PurpleGrey80),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column() {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = trip.status,
                    color = MediumGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = typeConfirmIcon.value,
                    contentDescription = "access",
                    tint = Purple,
                    modifier = Modifier.size(28.dp)
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = PurpleGrey80
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = TimeFormatter.toHHmm(trip.startTime),
                        color = MediumGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = TimeFormatter.toHHmm(trip.endTime),
                        color = MediumGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    Text(
                        text = trip.startLocationCity,
                        color = MediumGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = trip.endLocationCity,
                        color = MediumGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${trip.price} ₴",
                    modifier = Modifier.padding(16.dp),
                    color = MediumGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = PurpleGrey80
            )
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
               Icon(
                   imageVector = Icons.Default.AirlineSeatReclineExtra,
                   contentDescription = "available seats",
                   tint = MediumGray
               )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Кількість доступних місць: ${trip.availableSeats}",
                    color = MediumGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun checkTypeConfirmIcon(isFastConfirm: Boolean): ImageVector {
    return if (!isFastConfirm) {
        Icons.Default.AccessTime
    } else {
        Icons.Default.AutoMode
    }
}