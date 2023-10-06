package com.example.recipesapp.features.presentation.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.Visibility
import com.example.recipesapp.R
import com.example.recipesapp.features.presentation.recipes.adapters.RecipeAdapter
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeEvent
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeState
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeViewModel
import com.example.recipesapp.utils.CustomToast
import com.example.recipesapp.utils.models.QueryRequestModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private val recipesAdapter by lazy { RecipeAdapter() }

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    private lateinit var rview: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rview = inflater.inflate(R.layout.fragment_recipes, container, false)

        requestApi()
        setupRecyclerView()
        pullToRefresh()
        openFilterBottomSheet()

        return rview
    }

    private fun setupRecyclerView() {
        val recyclerView = rview.findViewById<RecyclerView>(R.id.rvRecipes)
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun pullToRefresh() {
        val swipeToRefresh = rview.findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh.setOnRefreshListener {
            recipeViewModel.onEvent(RecipeEvent.Reset)
            recipeViewModel.onEvent(RecipeEvent.GetRecipes(QueryRequestModel()))
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun openFilterBottomSheet() {
        val filterBtn = rview.findViewById<FloatingActionButton>(R.id.fabFilter)
        filterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_filterRecipeBottomsheet2)
        }
    }

    private fun requestApi() {
        val progressBar = rview.findViewById<ProgressBar>(R.id.loadingProgress)
        recipeViewModel.onEvent(RecipeEvent.GetRecipes(QueryRequestModel()))
        recipeViewModel.recipeState.observe(viewLifecycleOwner) { response ->
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