package com.example.recipesapp.features.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.R
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel

class Recipes_Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.recipes_card, container, false)

        val recipes = mutableListOf<RecipeResponseModel.Recipe>(

        )

//        val adapter = RecipesAdapter(recipes)
        val recipesRecyclerView = view?.findViewById<RecyclerView>(R.id.rvRecipes)
        recipesRecyclerView?.layoutManager = LinearLayoutManager(context)
//        recipesRecyclerView?.adapter = adapter

//        return adapter.onCreateViewHolder(container!!, 10)
        return view
    }
}