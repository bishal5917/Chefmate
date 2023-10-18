package com.example.recipesapp.features.presentation.recipes

import android.os.Bundle
import android.util.Log
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
import com.example.recipesapp.features.data.models.recipes.RecipeResponseModel
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

    private var endReached = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rview = inflater.inflate(R.layout.fragment_recipes, container, false)

//        requestApi()
        setupRecyclerView()
        pullToRefresh()
        openFilterBottomSheet()
        return rview
    }

    private fun setupRecyclerView() {
        val recyclerView = rview.findViewById<RecyclerView>(R.id.rvRecipes)
        recyclerView.adapter = recipesAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val total = layoutManager.itemCount
                val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisible >= total - 1 && !endReached) {
                    recipeViewModel.onEvent(
                        RecipeEvent.GetRecipes(isScrolling = true, startFetch = false)
                    )
                    endReached = true
                    Log.d("END", "END OF REC VIEW")
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun pullToRefresh() {
        val swipeToRefresh = rview.findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh.setOnRefreshListener {
            recipeViewModel.onEvent(RecipeEvent.Reset)
            recipeViewModel.onEvent(
                RecipeEvent.GetRecipes(isScrolling = false, startFetch = true)
            )
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
        val recView = rview.findViewById<RecyclerView>(R.id.rvRecipes)
        recipeViewModel.onEvent(RecipeEvent.GetRecipes(isScrolling = false, startFetch = true))
        recipeViewModel.recipeState.observe(viewLifecycleOwner) { response ->
            if (response.status == RecipeState.RecipeStatus.LOADING) {
                endReached = true
                progressBar.visibility = View.VISIBLE
            }
            if (response.status == RecipeState.RecipeStatus.SUCCESS) {
                progressBar.visibility = View.GONE
                response.recipes?.let { recipesAdapter.setData(response.recipes) }
                endReached = false
            }
            if (response.status == RecipeState.RecipeStatus.FAILED) {
                endReached = true
                progressBar.visibility = View.GONE
                CustomToast.showToast(context = requireContext(), "${response.message}")
            }
        }
    }
}