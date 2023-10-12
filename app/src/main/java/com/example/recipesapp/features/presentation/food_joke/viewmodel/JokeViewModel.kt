package com.example.recipesapp.features.presentation.food_joke.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.features.domain.usecases.GetFoodJokeUsecase
import com.example.recipesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(private val getFoodJokeUsecase: GetFoodJokeUsecase) :
    ViewModel() {
    private val _jokeState = MutableLiveData(JokeState.idle)
    val jokeState: LiveData<JokeState> = _jokeState

    fun onEvent(event: JokeEvent) {
        when (event) {
            is JokeEvent.GetJoke -> {
                getJoke()
            }
        }
    }

    private fun getJoke() {
        getFoodJokeUsecase.call().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _jokeState.postValue(
                        _jokeState.value?.copy(
                            status = JokeState.JokeStatus.LOADING,
                            message = "Getting Joke ..."
                        )
                    )
                }

                is Resource.Success -> {
                    _jokeState.postValue(
                        _jokeState.value?.copy(
                            status = JokeState.JokeStatus.SUCCESS,
                            message = "Joke fetched...",
                            joke = result.data,
                        )
                    )
                    Log.d("JokeViewModel", "API Response, ${result.data}")
                }

                is Resource.Error -> {
                    _jokeState.postValue(
                        _jokeState.value?.copy(
                            status = JokeState.JokeStatus.FAILED,
                            message = result.message,
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}