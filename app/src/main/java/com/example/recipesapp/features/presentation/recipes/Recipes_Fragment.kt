package com.example.recipesapp.features.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.compose.runtime.LaunchedEffect
import androidx.fragment.app.viewModels
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.R
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeEvent
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeState
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Recipes_Fragment : Fragment() {
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.recipes_card, container, false)

//        viewModel.recipeState.observe(viewLifecycleOwner, { recipeState ->
////             Update UI based on the observed recipeState
//            when {
////                recipeState.status == RecipeState.Status.LOADING->
//            }
//        })

//        val adapter = RecipesAdapter(recipes)
//        val recipesRecyclerView = view?.findViewById<RecyclerView>(R.id.rvRecipes)
//        recipesRecyclerView?.layoutManager = LinearLayoutManager(context)
//        recipesRecyclerView?.adapter = adapter

//        return adapter.onCreateViewHolder(container!!, 10)
        return view
    }
}