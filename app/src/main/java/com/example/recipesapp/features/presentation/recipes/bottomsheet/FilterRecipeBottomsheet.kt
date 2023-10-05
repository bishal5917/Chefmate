package com.example.recipesapp.features.presentation.recipes.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipesapp.R
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeEvent
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterRecipeBottomsheet : BottomSheetDialogFragment() {
    private lateinit var bottomSheetView: View
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bottomSheetView = inflater.inflate(
            R.layout.fragment_filter_recipe_bottomsheet, container,
            false
        )
        applyFilter()
        return bottomSheetView
    }

    private fun applyFilter() {
        val applyBtn = bottomSheetView.findViewById<Button>(R.id.btnApply)
        applyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_filterRecipeBottomsheet2_to_recipesFragment)
            recipeViewModel.onEvent(
                RecipeEvent.SelectRecipesFilter(
                    dietType = "vegan", mealType
                    = "vegan"
                )
            )
        }
    }
}