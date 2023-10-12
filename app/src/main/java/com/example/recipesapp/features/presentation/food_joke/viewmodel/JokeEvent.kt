package com.example.recipesapp.features.presentation.food_joke.viewmodel


sealed class JokeEvent {
    object GetJoke : JokeEvent()
}