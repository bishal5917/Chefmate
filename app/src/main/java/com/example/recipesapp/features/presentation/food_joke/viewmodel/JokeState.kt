package com.example.recipesapp.features.presentation.food_joke.viewmodel

import com.example.recipesapp.features.data.models.food_joke.FoodJokeResponseModel

data class JokeState(
    val status: JokeStatus,
    val message: String? = null,
    val joke: FoodJokeResponseModel? = null
) {
    companion object {
        val idle = JokeState(JokeStatus.IDLE, message = "Idle")
    }

    enum class JokeStatus {
        IDLE, LOADING, SUCCESS, FAILED
    }
}