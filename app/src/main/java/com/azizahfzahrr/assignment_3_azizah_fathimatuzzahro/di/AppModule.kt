package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.di

import AuthRepositoryImpl
import android.content.Context
import androidx.room.Room
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.AuthRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.ItineraryRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.ItineraryRepositoryImpl
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.PreferencesRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.PreferencesRepositoryImpl
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.TravelRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.TravelRepositoryImpl
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.UserRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.UserRepositoryImpl
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.local.UserPreferences
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryDao
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryDatabase
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase.FetchTravelDataUseCase
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase.FetchUserProfileUseCase
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase.LoginUseCase
import com.example.app.data.repository.DestinationRepositoryImpl
import com.example.app.domain.repository.DestinationRepository
import com.example.app.domain.usecase.DestinationDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(userPreferences: UserPreferences): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val token = runBlocking { userPreferences.getUserToken() }
            val requestBuilder = chain.request().newBuilder()
            if (token != null) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            chain.proceed(requestBuilder.build())
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://167.99.74.195:8090")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideTravelRepository(apiService: ApiService): TravelRepository {
        return TravelRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFetchTravelDataUseCase(travelRepository: TravelRepository): FetchTravelDataUseCase {
        return FetchTravelDataUseCase(travelRepository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDestinationRepository(apiService: ApiService): DestinationRepository {
        return DestinationRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDestinationDetailUseCase(destinationRepository: DestinationRepository): DestinationDetailUseCase {
        return DestinationDetailUseCase(destinationRepository)
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(apiService: ApiService): PreferencesRepository {
        return PreferencesRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFetchUserProfileUseCase(userRepository: UserRepository): FetchUserProfileUseCase {
        return FetchUserProfileUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ItineraryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ItineraryDatabase::class.java,
            "itinerary_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideItineraryDao(itineraryDatabase: ItineraryDatabase): ItineraryDao {
        return itineraryDatabase.itineraryDao()
    }

    @Provides
    @Singleton
    fun provideItineraryRepository(itineraryDao: ItineraryDao): ItineraryRepository {
        return ItineraryRepositoryImpl(itineraryDao)
    }
}