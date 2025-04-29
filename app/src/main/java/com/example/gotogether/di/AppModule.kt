package com.example.gotogether.di

import android.content.Context
import android.content.SharedPreferences
import com.example.gotogether.AuthInterceptor
import com.example.gotogether.data.activity_log.ActivityLogApiService
import com.example.gotogether.data.activity_log.repository.ActivityLogRepository
import com.example.gotogether.data.activity_log.repository.ActivityLogRepositoryImpl
import com.example.gotogether.data.car.CarApiService
import com.example.gotogether.data.car.repository.CarRepository
import com.example.gotogether.data.car.repository.CarRepositoryImpl
import com.example.gotogether.data.location.LocationApiService
import com.example.gotogether.data.location.repository.LocationRepository
import com.example.gotogether.data.location.repository.LocationRepositoryImpl
import com.example.gotogether.data.login.LoginApiService
import com.example.gotogether.data.login.repository.LoginRepository
import com.example.gotogether.data.login.repository.LoginRepositoryImpl
import com.example.gotogether.data.registration.RegistrationApiService
import com.example.gotogether.data.registration.repository.RegistrationRepository
import com.example.gotogether.data.registration.repository.RegistrationRepositoryImpl
import com.example.gotogether.data.review.ReviewApiService
import com.example.gotogether.data.review.repository.ReviewRepository
import com.example.gotogether.data.review.repository.ReviewRepositoryImpl
import com.example.gotogether.data.trip.TripApiService
import com.example.gotogether.data.trip.repository.TripRepository
import com.example.gotogether.data.trip.repository.TripRepositoryImpl
import com.example.gotogether.data.trip_passenger.TripPassengerApiService
import com.example.gotogether.data.trip_passenger.repository.TripPassengerRepository
import com.example.gotogether.data.trip_passenger.repository.TripPassengerRepositoryImpl
import com.example.gotogether.data.trip_request.TripRequestApiService
import com.example.gotogether.data.trip_request.repository.TripRequestRepository
import com.example.gotogether.data.trip_request.repository.TripRequestRepositoryImpl
import com.example.gotogether.data.user.UserApiService
import com.example.gotogether.data.user.repository.UserRepository
import com.example.gotogether.data.user.repository.UserRepositoryImpl
import com.example.gotogether.domain.activity_log.GetActivitiesUseCase
import com.example.gotogether.domain.activity_log.PostActivityUseCase
import com.example.gotogether.domain.car.GetCarsUseCase
import com.example.gotogether.domain.location.GetLocationsUseCase
import com.example.gotogether.domain.login.LoginUseCase
import com.example.gotogether.domain.registration.RegistrationUseCase
import com.example.gotogether.domain.review.GetRatingUseCase
import com.example.gotogether.domain.review.GetReviewsByReviewedUuidUseCase
import com.example.gotogether.domain.review.GetReviewsByReviewerUuidUseCase
import com.example.gotogether.domain.trip.usecase.DeleteTripUseCase
import com.example.gotogether.domain.trip.usecase.GetDetailedTripByIdUseCase
import com.example.gotogether.domain.trip.usecase.GetTripsByDateUseCase
import com.example.gotogether.domain.trip.usecase.GetTripsByDriverUuidUseCase
import com.example.gotogether.domain.trip.usecase.GetTripsByPassengerUuidUseCase
import com.example.gotogether.domain.trip.usecase.GetTripsByRequesterUuidUseCase
import com.example.gotogether.domain.trip.usecase.PostTripUseCase
import com.example.gotogether.domain.trip.usecase.PutTripUseCase
import com.example.gotogether.domain.trip_passenger.usecase.DeletePassengerUseCase
import com.example.gotogether.domain.trip_passenger.usecase.GetPassengersByTripIdUseCase
import com.example.gotogether.domain.trip_passenger.usecase.PostPassengerUseCase
import com.example.gotogether.domain.trip_request.usecase.DeleteTripRequestUseCase
import com.example.gotogether.domain.trip_request.usecase.GetTripRequestByTripIdAndUserUuidUseCase
import com.example.gotogether.domain.trip_request.usecase.GetTripRequestsByTripIdUseCase
import com.example.gotogether.domain.trip_request.usecase.GetTripRequestsByUserUuidUseCase
import com.example.gotogether.domain.trip_request.usecase.PostTripRequestUseCase
import com.example.gotogether.domain.trip_request.usecase.PutTripRequestUseCase
import com.example.gotogether.domain.user.usecase.DeleteUserUseCase
import com.example.gotogether.domain.user.usecase.GetCurrentUserUseCase
import com.example.gotogether.domain.user.usecase.GetUserByUuidUseCase
import com.example.gotogether.domain.user.usecase.UpdateUserUseCase
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
            .baseUrl("https://af6d-46-173-105-195.ngrok-free.app")
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
    fun provideUpdateUserUseCase(repository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(repository: UserRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(repository: UserRepository): DeleteUserUseCase {
        return DeleteUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        api: LoginApiService,
        retrofit: Retrofit,
        sharedPreferences: SharedPreferences,
    ): LoginRepository {
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
    fun provideTripRepository(api: TripApiService, retrofit: Retrofit): TripRepository {
        return TripRepositoryImpl(api, retrofit)
    }

    @Provides
    @Singleton
    fun provideGetTripsByDateUseCase(repository: TripRepository): GetTripsByDateUseCase {
        return GetTripsByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTripsByDriverUuid(repository: TripRepository): GetTripsByDriverUuidUseCase {
        return GetTripsByDriverUuidUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTripsByPassengerUuid(repository: TripRepository): GetTripsByPassengerUuidUseCase {
        return GetTripsByPassengerUuidUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideGetTripsByRequesterUuid(repository: TripRepository): GetTripsByRequesterUuidUseCase {
        return GetTripsByRequesterUuidUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDetailedTripByIdUseCase(repository: TripRepository): GetDetailedTripByIdUseCase {
        return GetDetailedTripByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePostTripUseCase(repository: TripRepository): PostTripUseCase {
        return PostTripUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTripUseCase(repository: TripRepository): DeleteTripUseCase {
        return DeleteTripUseCase(repository)
    }

@Provides
    @Singleton
    fun providePutTripUseCase(repository: TripRepository): PutTripUseCase {
        return PutTripUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTripPassengerApiService(retrofit: Retrofit): TripPassengerApiService {
        return retrofit.create(TripPassengerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTripPassengerRepository(
        api: TripPassengerApiService,
        retrofit: Retrofit,
    ): TripPassengerRepository {
        return TripPassengerRepositoryImpl(api, retrofit)
    }

    @Provides
    @Singleton
    fun provideGetPassengersByTripIdUseCase(repository: TripPassengerRepository): GetPassengersByTripIdUseCase {
        return GetPassengersByTripIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeletePassengerUseCase(repository: TripPassengerRepository): DeletePassengerUseCase {
        return DeletePassengerUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePostPassengerUseCase(repository: TripPassengerRepository): PostPassengerUseCase {
        return PostPassengerUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTripRequestApiService(retrofit: Retrofit): TripRequestApiService {
        return retrofit.create(TripRequestApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTripRequestRepository(api: TripRequestApiService): TripRequestRepository {
        return TripRequestRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDeleteTripRequestUseCase(repository: TripRequestRepository): DeleteTripRequestUseCase {
        return DeleteTripRequestUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTripRequestsByTripIdUseCase(repository: TripRequestRepository): GetTripRequestsByTripIdUseCase {
        return GetTripRequestsByTripIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTripRequestByTripIdAndUserUuidUseCase(repository: TripRequestRepository): GetTripRequestByTripIdAndUserUuidUseCase {
        return GetTripRequestByTripIdAndUserUuidUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePutTripRequestUseCase(repository: TripRequestRepository): PutTripRequestUseCase {
        return PutTripRequestUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideGetTripRequestsByUserUuidUseCase(repository: TripRequestRepository): GetTripRequestsByUserUuidUseCase {
        return GetTripRequestsByUserUuidUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePostTripRequestUseCase(repository: TripRequestRepository): PostTripRequestUseCase {
        return PostTripRequestUseCase(repository)
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
    fun provideGetReviewsByReviewedUuidUseCase(repository: ReviewRepository): GetReviewsByReviewedUuidUseCase {
        return GetReviewsByReviewedUuidUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetReviewsByReviewerUuidUseCase(repository: ReviewRepository): GetReviewsByReviewerUuidUseCase {
        return GetReviewsByReviewerUuidUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRatingUseCase(repository: ReviewRepositoryImpl): GetRatingUseCase {
        return GetRatingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCarApiService(retrofit: Retrofit): CarApiService {
        return retrofit.create(CarApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCarRepository(api: CarApiService): CarRepository {
        return CarRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetCarsUseCase(repository: CarRepository): GetCarsUseCase {
        return GetCarsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegistrationApiService(retrofit: Retrofit): RegistrationApiService {
        return retrofit.create(RegistrationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(
        api: RegistrationApiService,
    ): RegistrationRepository {
        return RegistrationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRegistrationUseCase(repository: RegistrationRepository): RegistrationUseCase {
        return RegistrationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideActivityLogApiService(retrofit: Retrofit): ActivityLogApiService {
        return retrofit.create(ActivityLogApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideActivityLogRepository(
        api: ActivityLogApiService,
    ): ActivityLogRepository {
        return ActivityLogRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePostActivityUseCase(repository: ActivityLogRepository): PostActivityUseCase {
        return PostActivityUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetActivitiesUseCase(repository: ActivityLogRepository): GetActivitiesUseCase {
        return GetActivitiesUseCase(repository)
    }
}