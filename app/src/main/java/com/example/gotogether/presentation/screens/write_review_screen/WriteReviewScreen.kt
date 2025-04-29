package com.example.gotogether.presentation.screens.write_review_screen

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotogether.data.user.UpdateUserRequest
import com.example.gotogether.domain.ReviewRating
import com.example.gotogether.domain.user.DrivingSkillsRating
import com.example.gotogether.presentation.components.auth.FieldError
import com.example.gotogether.presentation.components.user_info.UserInfoParameter
import com.example.gotogether.presentation.components.user_info.UserInfoTextField
import com.example.gotogether.presentation.screens.detailed_trip_screen.handleResult
import com.example.gotogether.presentation.screens.reviewable_list_trip_screen.ReviewableUsersList
import com.example.gotogether.ui.theme.DarkGray
import com.example.gotogether.ui.theme.MediumGray
import com.example.gotogether.ui.theme.Purple
import com.example.gotogether.ui.theme.PurpleGrey80
import com.example.gotogether.utils.validation.UserValidator
import kotlinx.coroutines.launch

@Composable
fun WriteReviewScreen(
    writeReviewViewModel: WriteReviewViewModel = hiltViewModel<WriteReviewViewModel>(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val writeReviewState = writeReviewViewModel.state.collectAsState()
    val rating: MutableState<Int?> = rememberSaveable  { mutableStateOf(null) }
    val drivingSkills: MutableState<Int?> = rememberSaveable  { mutableStateOf(null) }
    val isMarkedDrivingSkill = rememberSaveable  { mutableStateOf(false) }
    val comment: MutableState<String> = remember { mutableStateOf("") }
    val commentFocusRequester = remember { FocusRequester() }
    var wasCommentTouched by remember { mutableStateOf(false) }
    var commentError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var step by rememberSaveable { mutableStateOf(ReviewStep.RATING) }
    val insertReviewResult = writeReviewViewModel.insertReviewResult


    LaunchedEffect(insertReviewResult) {
        insertReviewResult?.let {
            handleResult(it, context, navController, destination = "search_trips")
        }
    }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (writeReviewState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run { align(Alignment.Center) }
            )
        } else {
            writeReviewState.value.reviewedUser?.onSuccess {reviewedUser ->
                val currentUser = writeReviewState.value.currentUser?.onSuccess { it }?.getOrNull()
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Purple
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    when (step) {
                        ReviewStep.RATING -> {

                                Text(
                                    text = "Як пройшла ваша поїздка з ${reviewedUser.firstName}?",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = MediumGray,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                                ) {
                                    items(ReviewRating.entries.size) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clickable(
                                                    onClick = {
                                                        rating.value = ReviewRating.entries[it].rating
                                                        step = if (writeReviewViewModel.isReviewedUserDriver()) ReviewStep.DRIVING else ReviewStep.COMMENT
                                                    }
                                                )
                                        ) {
                                            Text(
                                                text = ReviewRating.entries[it].type,
                                                color = DarkGray,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 16.dp)
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                contentDescription = "right",
                                                tint = DarkGray
                                            )
                                        }
                                        HorizontalDivider(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            thickness = 2.dp,
                                            color = PurpleGrey80
                                        )
                                    }
                                }
                        }

                        ReviewStep.DRIVING -> {
                            Text(
                                text = "Наскільки вправно водить ${reviewedUser.firstName}?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = MediumGray,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn(
                                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                            ) {
                                items(DrivingSkillsRating.entries.size) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable(
                                                onClick = {
                                                    drivingSkills.value =
                                                        DrivingSkillsRating.entries[it].rating
                                                    step = ReviewStep.COMMENT
                                                }
                                            )
                                    ) {
                                        Text(
                                            text = DrivingSkillsRating.entries[it].type,
                                            color = DarkGray,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                            contentDescription = "right",
                                            tint = DarkGray
                                        )
                                    }
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        thickness = 2.dp,
                                        color = PurpleGrey80
                                    )
                                }
                            }
                        }

                        ReviewStep.COMMENT -> {
                            Text(
                                text = "Опишіть поїздку з ${reviewedUser.firstName}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = MediumGray,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),

                                ) {
                                Text(
                                    text = "Опис про себе",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = MediumGray
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                UserInfoTextField(
                                    input = comment,
                                    inputFocusRequester = commentFocusRequester,
                                    userInfoParameter = UserInfoParameter.DESCRIPTION,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp),
                                    singleLine = false,
                                    keyboardActions = KeyboardActions(
                                        onNext = { commentFocusRequester.requestFocus() }
                                    ),
                                    onValueChange = {
                                        comment.value = it
                                        wasCommentTouched = true
                                        commentError = if (wasCommentTouched) {
                                            UserValidator.validateComment(comment.value)
                                        } else {
                                            null
                                        }
                                    }
                                )
                                FieldError(
                                    inputError = commentError,
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                                Button(
                                    onClick = {
                                        scope.launch {
                                            if(currentUser != null)
                                                writeReviewViewModel.createReview(
                                                    currentUserUuid = currentUser.userUuid,
                                                    rating = rating.value ?: 3,
                                                    drivingSkills = drivingSkills.value,
                                                    comment = comment.value
                                                )
                                        }
                                        // navController.popBackStack()
                                    },
                                    modifier = Modifier
                                        //  .align(Alignment.BottomCenter)
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

                        }
                    }
                }
            }?.onFailure {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Помилка завантаження даних")
                }
            }
        }

    }

}

enum class ReviewStep {
    RATING,
    DRIVING,
    COMMENT
}