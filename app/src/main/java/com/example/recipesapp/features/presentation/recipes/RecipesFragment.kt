package com.example.recipesapp.features.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.recipesapp.R
import com.example.recipesapp.features.presentation.recipes.adapters.RecipeAdapter
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeEvent
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeState
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeViewModel
import com.example.recipesapp.utils.CustomToast
import com.example.recipesapp.utils.models.QueryRequestModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private val recipesAdapter by lazy { RecipeAdapter() }
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var rview: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rview = inflater.inflate(R.layout.fragment_recipes, container, false)

        setupRecyclerView()
        requestApi()
        return rview
    }

    private fun setupRecyclerView() {
        val recyclerView = rview.findViewById<RecyclerView>(R.id.rvRecipes)
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun requestApi() {
        val progressBar = rview.findViewById<ProgressBar>(R.id.loadingProgress)
        viewModel.onEvent(RecipeEvent.GetRecipes(QueryRequestModel(addRecipeInformation = true)))
        viewModel.recipeState.observe(viewLifecycleOwner) { response ->
            if (response.status == RecipeState.RecipeStatus.LOADING) {
                progressBar.visibility = View.VISIBLE
            }
            if (response.status == RecipeState.RecipeStatus.SUCCESS) {
                progressBar.visibility = View.GONE
                response.recipes?.results.let { recipesAdapter.setData(response.recipes!!) }
            }
            if (response.status == RecipeState.RecipeStatus.FAILED) {
                progressBar.visibility = View.GONE
                CustomToast.showToast(context = requireContext(), "${response.message}")
            }
        }
    }
}