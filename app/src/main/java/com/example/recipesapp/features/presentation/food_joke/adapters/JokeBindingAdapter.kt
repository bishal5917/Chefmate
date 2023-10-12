package com.example.recipesapp.features.presentation.food_joke.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

class JokeBindingAdapter {
    companion object {
        @BindingAdapter("setJokeText")
        @JvmStatic
        fun setJokeText(textView: TextView, joke: String?) {
            textView.text = joke
        }
    }
}