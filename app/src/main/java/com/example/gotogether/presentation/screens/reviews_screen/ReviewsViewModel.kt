package com.example.gotogether.presentation.screens.reviews_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotogether.domain.review.GetRatingUseCase
import com.example.gotogether.domain.review.GetReviewsByReviewedUuidUseCase
import com.example.gotogether.domain.review.Rating
import com.example.gotogether.domain.review.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getReviewsUseCase: GetReviewsByReviewedUuidUseCase,
    private val getRatingUseCase: GetRatingUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(ReviewState())
    val state = _state.asStateFlow()

    private val userUuid: String = savedStateHandle.get<String>("userUuid")!!

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            _state.update {
                it.copy(
                    reviews = getReviews(userUuid),
                    rating = getRatings(userUuid),
                    isLoading = false
                )
            }
        }
    }

    suspend fun getReviews(userUuid: String): Result<List<Review>> = getReviewsUseCase(userUuid)
    suspend fun getRatings(userUuid: String): Result<Rating> = getRatingUseCase(userUuid)

    data class ReviewState(
        val reviews: Result<List<Review>>? = null,
        val rating: Result<Rating>? = null,
        val isLoading: Boolean = false,
    )
}