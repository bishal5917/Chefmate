package com.example.recipesapp.features.presentation.recipes.adapters

import android.app.DirectAction
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.recipesapp.R
import com.example.recipesapp.features.presentation.recipes.RecipesFragmentDirections

class RecipeBindingAdapter {
    companion object {
        @BindingAdapter("onRecipeClickedListener")
        @JvmStatic
        fun onRecipeClickedListener(recipeCard: ConstraintLayout, id: Int) {
            recipeCard.setOnClickListener {
                try {
                    val action = RecipesFragmentDirections
                        .actionRecipesFragmentToRecipeDetailActivity(id)
                    recipeCard.findNavController()
                        .navigate(action)
                } catch (ex: Exception) {
                    Log.d("setOnClickListener", "${ex.message}")
                }
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setPreparationMinutes")
        @JvmStatic
        fun setPreparationMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context, R.color.secondaryColor
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context, R.color.secondaryColor
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                placeholder(R.drawable.placeholder)
                error(R.drawable.error)
            }
        }
    }
}
