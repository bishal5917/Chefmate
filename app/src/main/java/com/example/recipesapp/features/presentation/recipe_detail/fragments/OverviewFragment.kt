package com.example.recipesapp.features.presentation.recipe_detail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import com.example.recipesapp.R
import com.example.recipesapp.databinding.FragmentOverviewBinding
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailState
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailViewmodel
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeState
import com.example.recipesapp.utils.CustomToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    @Inject
    lateinit var recipeDetailViewModel: RecipeDetailViewmodel

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout with data binding
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_overview, container, false
        )
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData() {
        val progressBar = binding.pbRecipeOverview
        recipeDetailViewModel.recipeDetailState.observe(viewLifecycleOwner) { response ->
            if (response.status == RecipeDetailState.RecipeDetailStatus.LOADING) {
                progressBar.visibility = View.VISIBLE
            }
            if (response.status == RecipeDetailState.RecipeDetailStatus.SUCCESS) {
                progressBar.visibility = View.GONE
                binding.result = response.recipeDetail
            }
            if (response.status == RecipeDetailState.RecipeDetailStatus.FAILED) {
                progressBar.visibility = View.GONE
                CustomToast.showToast(context = requireContext(), "${response.message}")
            }
        }
    }
}