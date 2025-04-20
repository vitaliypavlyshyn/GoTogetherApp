package com.example.gotogether.di

import android.content.Context
import android.content.SharedPreferences
import com.example.gotogether.AuthInterceptor
import com.example.gotogether.data.location.LocationApiService
import com.example.gotogether.data.location.repository.LocationRepository
import com.example.gotogether.data.location.repository.LocationRepositoryImpl
import com.example.gotogether.data.login.LoginApiService
import com.example.gotogether.data.login.repository.LoginRepository
import com.example.gotogether.data.login.repository.LoginRepositoryImpl
import com.example.gotogether.data.review.ReviewApiService
import com.example.gotogether.data.review.repository.ReviewRepository
import com.example.gotogether.data.review.repository.ReviewRepositoryImpl
import com.example.gotogether.data.trip.TripApiService
import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.data.trip.repository.TripRepositoryImpl
import com.example.gotogether.data.trip_passenger.TripPassengerApiService
import com.example.gotogether.data.trip_passenger.repository.TripPassengerRepository
import com.example.gotogether.data.trip_passenger.repository.TripPassengerRepositoryImpl
import com.example.gotogether.data.user.UserApiService
import com.example.gotogether.data.user.repository.UserRepository
import com.example.gotogether.data.user.repository.UserRepositoryImpl
import com.example.gotogether.domain.location.GetLocationsUseCase
import com.example.gotogether.domain.login.LoginUseCase
import com.example.gotogether.domain.review.GetRatingUseCase
import com.example.gotogether.domain.review.GetReviewsUseCase
import com.example.gotogether.domain.trip.GetDetailedTripByIdUseCase
import com.example.gotogether.domain.trip.GetTripsByDateUseCase
import com.example.gotogether.domain.trip_passenger.GetPassengersByTripIdUseCase
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import com.example.gotogether.domain.user.usecase.GetUserByUuidUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://d989-46-173-105-195.ngrok-free.app")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApiService): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetUserByUuidUseCase(repository: UserRepository): GetUserByUuidUseCase {
        return GetUserByUuidUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(repository: UserRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApiService, retrofit: Retrofit, sharedPreferences: SharedPreferences): LoginRepository {
        return LoginRepositoryImpl(api, retrofit, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLocationApiService(retrofit: Retrofit): LocationApiService {
        return retrofit.create(LocationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(api: LocationApiService): LocationRepository {
        return LocationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetLocationsUseCase(repository: LocationRepository): GetLocationsUseCase {
        return GetLocationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTripApiService(retrofit: Retrofit): TripApiService {
        return retrofit.create(TripApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTripRepository(api: TripApiService): TripRepository {
        return TripRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetTripsByDateUseCase(repository: TripRepository): GetTripsByDateUseCase {
        return GetTripsByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDetailedTripByIdUseCase(repository: TripRepository): GetDetailedTripByIdUseCase {
        return GetDetailedTripByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTripPassengerApiService(retrofit: Retrofit): TripPassengerApiService {
        return retrofit.create(TripPassengerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTripPassengerRepository(api: TripPassengerApiService): TripPassengerRepository {
        return TripPassengerRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetPassengersByTripIdUseCase(repository: TripPassengerRepository): GetPassengersByTripIdUseCase {
        return GetPassengersByTripIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideReviewApiService(retrofit: Retrofit): ReviewApiService {
        return retrofit.create(ReviewApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewRepository(api: ReviewApiService): ReviewRepository {
        return ReviewRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetReviewsUseCase(repository: ReviewRepository): GetReviewsUseCase {
        return GetReviewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRatingUseCase(repository: ReviewRepositoryImpl): GetRatingUseCase {
        return GetRatingUseCase(repository)
    }
}