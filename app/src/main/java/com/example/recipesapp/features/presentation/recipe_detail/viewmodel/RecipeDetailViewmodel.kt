package com.example.recipesapp.features.presentation.recipe_detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.features.data.models.recipe_detail.RecipeRequestModel
import com.example.recipesapp.features.domain.usecases.GetRecipeDetailUsecase
import com.example.recipesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewmodel @Inject constructor(
    private val getRecipeDetailUsecase: GetRecipeDetailUsecase
) : ViewModel() {
    private val _recipeDetailState = MutableLiveData(RecipeDetailState.idle)
    val recipeDetailState: LiveData<RecipeDetailState> = _recipeDetailState

    fun onEvent(event: RecipeDetailEvent) {
        when (event) {
            is RecipeDetailEvent.GetRecipeDetail -> {
                getRecipeDetail(event.recipeRequestModel)
            }
        }
    }

    private fun getRecipeDetail(recipeRequestModel: RecipeRequestModel) {
        getRecipeDetailUsecase.call(recipeRequestModel).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _recipeDetailState.postValue(
                        _recipeDetailState.value?.copy(
                            status = RecipeDetailState.RecipeDetailStatus.LOADING,
                            message = "Getting Recipes..."
                        )
                    )
                }
                is Resource.Success -> {
                    _recipeDetailState.postValue(
                        _recipeDetailState.value?.copy(
                            status = RecipeDetailState.RecipeDetailStatus.SUCCESS,
                            message = "Recipe details fetched...",
                            recipeDetail = result.data,
                        )
                    )
                    Log.d("RecipeViewModel", "API Response, ${result.data}")
                }
                is Resource.Error -> {
                    _recipeDetailState.postValue(
                        _recipeDetailState.value?.copy(
                            status = RecipeDetailState.RecipeDetailStatus.FAILED,
                            message = result.message.toString()
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}