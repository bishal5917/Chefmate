package com.example.recipesapp.features.presentation.recipes.viewmodel

import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel

data class RecipeState(
    val status: Status, val message: String? = null, val recipes: RecipeResponseModel? = null
) {
    companion object {
        val IDLE = RecipeState(Status.IDLE, message = "Idle")
    }

    enum class Status {
        IDLE, LOADING, SUCCESS, FAILED
    }
}