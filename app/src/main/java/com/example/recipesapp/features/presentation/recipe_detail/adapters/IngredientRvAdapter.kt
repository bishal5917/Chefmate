package com.example.recipesapp.features.presentation.recipe_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.IngredientsCardBinding
import com.example.recipesapp.databinding.RecipesCardBinding
import com.example.recipesapp.features.data.models.recipe_detail.RecipeDetailResponseModel
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.utils.IngredientDiffUtil
import com.example.recipesapp.utils.RecipesDiffUtil

class IngredientRvAdapter : RecyclerView.Adapter<IngredientRvAdapter.MyViewHolder>() {

    private var ingredient = emptyList<RecipeDetailResponseModel.ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: RecipeDetailResponseModel.ExtendedIngredient) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsCardBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = ingredient[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return ingredient.size
    }

    fun setData(newData: RecipeDetailResponseModel) {
        val ingredientDiffUtil =
            IngredientDiffUtil(
                ingredient,
                newData.extendedIngredients as List<RecipeDetailResponseModel.ExtendedIngredient>
            )
        val diffUtilResult = DiffUtil.calculateDiff(ingredientDiffUtil)
        ingredient = newData.extendedIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}