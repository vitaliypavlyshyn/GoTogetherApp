package com.example.gotogether.presentation.screens.reviewable_list_trip_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.R
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple

@Composable
fun ReviewableListTripScreen(
    reviewableListTripViewModel: ReviewableListTripViewModel = hiltViewModel<ReviewableListTripViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val reviewableListTripState = reviewableListTripViewModel.state.collectAsState()
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (reviewableListTripState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            reviewableListTripState.value.reviews?.onSuccess {
                ReviewableUsersList(
                    reviewableUsers = reviewableListTripState.value.reviewableUsers,
                    navController = navController,
                )
            }?.onFailure {

            }
        }
    }

}

@Composable
fun ReviewableUsersList(
    reviewableUsers: List<ReviewableListTripViewModel.UserPreview>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        IconButton(
            modifier = Modifier.padding(16.dp),
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Залиште відгук",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = MediumGray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 40.dp)
        ) {
            items(reviewableUsers.size) {
                Row() {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {

                                    navController.navigate("write_review/${reviewableUsers[it].tripId}/${reviewableUsers[it].uuid}")
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

                        Text(
                            text = reviewableUsers[it].name,
                            color = DarkGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )


                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "right",
                            tint = DarkGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}