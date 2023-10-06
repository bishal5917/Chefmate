package com.example.recipesapp.features.presentation.recipes.viewmodel

import com.example.recipesapp.utils.models.QueryRequestModel
import com.google.android.material.chip.ChipGroup

sealed class RecipeEvent {
    data class GetRecipes(val queryRequestModel: QueryRequestModel) : RecipeEvent()
    data class SelectRecipesFilter(
        val mealType: String, val dietType: String, val
        mealTypeChipId: Int, val dietTypeChipId: Int
    ) :
        RecipeEvent()

    data class PersistSelectedChips(val mealChipGrId: ChipGroup, val dietChipGrId: ChipGroup) :
        RecipeEvent()
}