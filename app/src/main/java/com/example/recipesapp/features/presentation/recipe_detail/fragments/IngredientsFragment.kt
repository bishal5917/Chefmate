package com.example.recipesapp.features.presentation.recipe_detail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.R
import com.example.recipesapp.features.presentation.recipe_detail.adapters.IngredientRvAdapter
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailState
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailViewmodel
import com.example.recipesapp.features.presentation.recipes.adapters.RecipeAdapter
import com.example.recipesapp.utils.CustomToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IngredientsFragment : Fragment() {
    @Inject
    lateinit var recipeDetailViewModel: RecipeDetailViewmodel

    private val ingredientsAdapter by lazy { IngredientRvAdapter() }

    private lateinit var myview: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_ingredients, container, false)

        setupRecyclerView()
        observeLiveData()

        return myview
    }

    private fun setupRecyclerView() {
        val recyclerView = myview.findViewById<RecyclerView>(R.id.rvIngredients)
        recyclerView.adapter = ingredientsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeLiveData() {
        val progressBar = myview.findViewById<ProgressBar>(R.id.pbIngredients)
        recipeDetailViewModel.recipeDetailState.observe(viewLifecycleOwner) { response ->
            if (response.status == RecipeDetailState.RecipeDetailStatus.LOADING) {
                progressBar.visibility = View.VISIBLE
            }
            if (response.status == RecipeDetailState.RecipeDetailStatus.SUCCESS) {
               progressBar.visibility = View.GONE
                response.recipeDetail.let {
                    ingredientsAdapter.setData(response.recipeDetail!!)
                }
            }
            if (response.status == RecipeDetailState.RecipeDetailStatus.FAILED) {
                progressBar.visibility = View.GONE
                CustomToast.showToast(context = requireContext(), "${response.message}")
            }
        }
    }
}