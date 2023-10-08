package com.example.recipesapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.recipesapp.features.data.models.recipe_detail.RecipeDetailResponseModel

class IngredientDiffUtil(
    private val oldList: List<RecipeDetailResponseModel.ExtendedIngredient>, private val newList:
    List<RecipeDetailResponseModel.ExtendedIngredient>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}