package com.example.recipesapp.di

import com.example.recipesapp.core.configs.ApiConfig
import com.example.recipesapp.features.data.datasource.UserRemoteDatasource
import com.example.recipesapp.features.data.datasource.UserRemoteDatasourceImpl
import com.example.recipesapp.features.data.repositories.UserRepositoryImpl
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.example.recipesapp.features.domain.usecases.GetRecipeUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(ApiConfig.baseUrl).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    fun provideDataSource(): UserRemoteDatasource {
        return UserRemoteDatasourceImpl()
    }

    @Provides
    fun provideRepository(dataSource: UserRemoteDatasource): UserRepository {
        return UserRepositoryImpl(dataSource)
    }

    //registering usecases
    @Provides
    fun provideRecipesUsecase(repo: UserRepository): GetRecipeUsecase {
        return GetRecipeUsecase(repo)
    }
//    @Singleton
//    @Provides
//    fun provideApiHandler(retrofit: Retrofit): FoodRecipesApi {
//        return retrofit.create(FoodRecipesApi::class.java)
//    }
}