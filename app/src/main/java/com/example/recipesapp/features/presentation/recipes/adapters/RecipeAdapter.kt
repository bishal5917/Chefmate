package com.example.recipesapp.features.presentation.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.RecipesCardBinding
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.utils.RecipesDiffUtil

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    private var recipe = emptyList<RecipeResponseModel.Recipe>()

    class MyViewHolder(private val binding: RecipesCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: RecipeResponseModel.Recipe) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesCardBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    fun setData(newData: List<RecipeResponseModel.Recipe>) {
        val recipesDiffUtil = RecipesDiffUtil(recipe, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipe = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}