package com.example.recipesapp.features.presentation.recipes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.domain.usecases.GetRecipeUsecase
import com.example.recipesapp.utils.Network.isNetworkAvailable
import com.example.recipesapp.utils.Resource
import com.example.recipesapp.utils.models.QueryRequestModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val getRecipeUsecase: GetRecipeUsecase) :
    ViewModel() {

    private val _recipeState = MutableLiveData(RecipeState.idle)
    val recipeState: LiveData<RecipeState> = _recipeState
    private var offset: Int = 0;

    fun onEvent(event: RecipeEvent) {
        when (event) {
            is RecipeEvent.GetRecipes -> {
                getRecipes(event.isScrolling, event.startFetch)
            }

            is RecipeEvent.SelectRecipesFilter -> {
                _recipeState.value = _recipeState.value?.copy(
                    mealType = event.mealType,
                    dietType = event.dietType,
                    dietTypeChipId = if (event.dietTypeChipId != 0) event.dietTypeChipId else _recipeState.value!!.dietTypeChipId,
                    mealTypeChipId = if (event.mealTypeChipId != 0) event.mealTypeChipId else _recipeState.value!!.mealTypeChipId,
                    query = event.query,
                )
                Log.d(
                    "SelChips",
                    "${_recipeState.value?.mealTypeChipId}-${_recipeState.value?.dietTypeChipId} "
                )
            }

            is RecipeEvent.PersistSelectedChips -> {
                persistSelectedChips(event.mealChipGrId, event.dietChipGrId)
            }

            is RecipeEvent.Reset -> {
                reset()
            }
        }
    }

    private fun getRecipes(isScrolling: Boolean, startFetch: Boolean) {
        if (_recipeState.value?.status == RecipeState.RecipeStatus.LOADING || _recipeState.value?.status == RecipeState.RecipeStatus.FAILED) {
            return
        }
        val queryRequestModel = QueryRequestModel(
            diet = _recipeState.value?.dietType, type = _recipeState.value?.mealType,
            addRecipeInformation = true,
            query = _recipeState.value?.query,
            offset = offset,
        )
        if (isScrolling == false && startFetch == true) {
            offset = 0;
            _recipeState.postValue(
                _recipeState.value?.copy(
                    status = RecipeState.RecipeStatus.LOADING,
                    message = "Getting Recipes...",
                    recipes = null
                )
            )
        }
        getRecipeUsecase.call(queryRequestModel).onEach { result ->
            when (result) {
//                is Resource.Loading -> {
//                    _recipeState.postValue(
//                        _recipeState.value?.copy(
//                            status = RecipeState.RecipeStatus.LOADING,
//                            message = "Getting Recipes..."
//                        )
//                    )
//                }
                is Resource.Success -> {
                    _recipeState.postValue(
                        _recipeState.value?.copy(
                            status = RecipeState.RecipeStatus.SUCCESS,
                            message = "Recipes fetched...",
                            recipes = result.data?.results as List<RecipeResponseModel.Recipe>?,
                        )
                    )
                    offset += 1
                    Log.d("Get DATA", "API Response, ${_recipeState.value?.recipes}")
                }

                is Resource.Error -> {
                    _recipeState.postValue(
                        _recipeState.value?.copy(
                            status = RecipeState.RecipeStatus.FAILED,
                            message = result.message.toString()
                        )
                    )
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun persistSelectedChips(mealChipGr: ChipGroup, dietChipGr: ChipGroup) {
        if (_recipeState.value?.mealTypeChipId != 0) {
            mealChipGr.findViewById<Chip>(_recipeState.value?.mealTypeChipId!!).isChecked = true
        }
        if (_recipeState.value?.dietTypeChipId != 0) {
            dietChipGr.findViewById<Chip>(_recipeState.value?.dietTypeChipId!!).isChecked = true
        }
    }

    private fun reset() {
        _recipeState.value = _recipeState.value?.copy(query = "")
    }
}
