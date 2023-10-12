package com.example.recipesapp.di

import com.example.recipesapp.database.RecipesDao
import com.example.recipesapp.features.data.datasource.LocalDatasource
import com.example.recipesapp.features.data.datasource.LocalDatasourceImpl
import com.example.recipesapp.features.data.datasource.UserRemoteDatasource
import com.example.recipesapp.features.data.datasource.UserRemoteDatasourceImpl
import com.example.recipesapp.features.data.repositories.UserRepositoryImpl
import com.example.recipesapp.features.domain.repositories.UserRepository
import com.example.recipesapp.features.domain.usecases.GetRecipeDetailUsecase
import com.example.recipesapp.features.domain.usecases.GetRecipeUsecase
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailViewmodel
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeViewModel
import com.example.recipesapp.services.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): UserRemoteDatasource {
        return UserRemoteDatasourceImpl(apiService)
    }

    @Provides
    fun provideLocalDataSource(recipesDao: RecipesDao): LocalDatasource {
        return LocalDatasourceImpl(recipesDao)
    }

    @Provides
    fun provideRepository(
        remoteDataSource: UserRemoteDatasource,
        localDatasource: LocalDatasource
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource, localDatasource)
    }

    //registering usecases
    @Provides
    fun provideRecipesUsecase(repo: UserRepository): GetRecipeUsecase {
        return GetRecipeUsecase(repo)
    }

    @Provides
    fun provideRecipeDetailUsecase(repo: UserRepository): GetRecipeDetailUsecase {
        return GetRecipeDetailUsecase(repo)
    }

    //providing viewmodel
    @Provides
    @Singleton
    fun providesRecipeViewModel(getRecipeUsecase: GetRecipeUsecase): RecipeViewModel {
        return RecipeViewModel(getRecipeUsecase)
    }

    @Provides
    @Singleton
    fun providesRecipeDetailViewModel(getRecipeDetailUsecase: GetRecipeDetailUsecase):
            RecipeDetailViewmodel {
        return RecipeDetailViewmodel(getRecipeDetailUsecase)
    }
}