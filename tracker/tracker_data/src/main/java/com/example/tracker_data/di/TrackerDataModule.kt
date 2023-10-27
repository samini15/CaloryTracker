package com.example.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.example.tracker_data.local.TrackerDao
import com.example.tracker_data.local.TrackerDatabase
import com.example.tracker_data.remote.OpenFoodApiService
import com.example.tracker_data.repository.TrackerRepositoryImpl
import com.example.tracker_domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

    @Provides
    @Singleton
    fun provideOpenFoodApiService(okHttpClient: OkHttpClient): OpenFoodApiService =
        Retrofit.Builder()
            .baseUrl(OpenFoodApiService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()

    // Database
    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): TrackerDatabase =
        Room.databaseBuilder(
            app,
            TrackerDatabase::class.java,
            TrackerDatabase.TRACKER_DB
        ).build()

    @Provides
    @Singleton
    fun provideTrackerRepository(
        db: TrackerDatabase,
        apiService: OpenFoodApiService
    ): TrackerRepository =
        TrackerRepositoryImpl(trackerDao = db.trackerDao, apiService = apiService)
}