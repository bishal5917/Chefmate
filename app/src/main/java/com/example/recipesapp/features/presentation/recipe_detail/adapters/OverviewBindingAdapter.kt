package com.example.recipesapp.features.presentation.recipe_detail.adapters

import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.recipesapp.R

class OverviewBindingAdapter {
    companion object {
        @BindingAdapter("setRecipeDetailTitle")
        @JvmStatic
        fun setRecipeDetailTitle(textView: TextView, title: String?) {
            textView.text = title
        }

        @BindingAdapter("setSummary")
        @JvmStatic
        fun setSummary(textView: TextView, summary: String?) {
            textView.text = summary
        }

        @BindingAdapter("setTypeColor")
        @JvmStatic
        fun setTypeColor(imageView: ImageView, checkStat: Boolean) {
            if (checkStat) {
                imageView.setColorFilter(
                    ContextCompat.getColor(imageView.context, R.color.secondaryColor),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }

        @BindingAdapter("loadRecipeDetailImage")
        @JvmStatic
        fun loadRecipeDetailImage(imageView: ImageView, imageUrl: String?) {
            imageView.load(imageUrl) {
                crossfade(600)
                placeholder(R.drawable.placeholder)
                error(R.drawable.error)
            }
        }
    }
}
