package com.example.recipesapp.features.presentation.recipe_detail.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.recipesapp.R
import com.example.recipesapp.core.configs.ApiConfig
import java.util.*

class IngredientBindingAdapter {
    companion object {
        @BindingAdapter("setIngredientTextViewContent")
        @JvmStatic
        fun setIngredientTextViewContent(textView: TextView, title: String?) {
            textView.text = title?.replaceFirstChar { it.uppercase() }
        }

        @BindingAdapter("setIngredientAmount")
        @JvmStatic
        fun setIngredientAmount(textView: TextView, amount: Double?) {
            textView.text = amount.toString()
        }

        @BindingAdapter("setIngredientImage")
        @JvmStatic
        fun setIngredientImage(imageView: ImageView, imageUrl: String?) {
            val ingredientImage = ApiConfig.ingredientImgBaseUrl + imageUrl
            imageView.load(ingredientImage) {
                crossfade(600)
                placeholder(R.drawable.placeholder)
                error(R.drawable.error)
            }
        }
    }
}
