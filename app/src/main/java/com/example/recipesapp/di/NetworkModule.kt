package com.example.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipesapp.core.configs.ApiConfig
import com.example.recipesapp.database.RecipesDatabase
import com.example.recipesapp.services.network.ApiService
import com.example.recipesapp.utils.Constants.DATABASE_NAME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
//import okhttp3.logging.HttpLoggingInterceptor
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
//        val interceptor = HttpLoggingInterceptor().apply {
//            this.level = HttpLoggingInterceptor.Level.BODY
//        }
        val client = OkHttpClient.Builder().apply {
            this.connectTimeout(30, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
            .baseUrl(ApiConfig.baseUrl).build()
    }

    @Provides
    @Singleton
    fun providesFlyBuyApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

    //local database
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}