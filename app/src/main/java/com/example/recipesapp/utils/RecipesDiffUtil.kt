package com.example.recipesapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel

class RecipesDiffUtil(
    private val oldList: List<RecipeResponseModel.Recipe>, private val newList:
    List<RecipeResponseModel.Recipe>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }

            oldList[oldItemPosition].image != newList[newItemPosition].image -> {
                false
            }

            oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                false
            }

            oldList[oldItemPosition].readyInMinutes != newList[newItemPosition].readyInMinutes -> {
                false
            }

            oldList[oldItemPosition].summary != newList[newItemPosition].summary -> {
                false
            }

            oldList[oldItemPosition].aggregateLikes != newList[newItemPosition].aggregateLikes -> {
                false
            }

            else -> true
        }
    }
}