package com.example.recipesapp.features.presentation.recipe_detail.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.recipesapp.R

class IngredientBindingAdapter {
    companion object {
        @BindingAdapter("setIngredientTextViewContent")
        @JvmStatic
        fun setIngredientTextViewContent(textView: TextView, title: String?) {
            textView.text = title
        }

        @BindingAdapter("setIngredientAmount")
        @JvmStatic
        fun setIngredientAmount(textView: TextView, amount: Double?) {
            textView.text = amount.toString()
        }

        @BindingAdapter("loadIngredientImage")
        @JvmStatic
        fun loadIngredientImage(imageView: ImageView, imageUrl: String?) {
            imageView.load(imageUrl) {
                crossfade(600)
                placeholder(R.drawable.placeholder)
                error(R.drawable.error)
            }
        }
    }
}
