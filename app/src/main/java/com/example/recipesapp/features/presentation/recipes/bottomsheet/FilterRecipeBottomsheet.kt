package com.example.recipesapp.features.presentation.recipes.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipesapp.R
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeEvent
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FilterRecipeBottomsheet : BottomSheetDialogFragment() {
    private lateinit var bottomSheetView: View

    @Inject
    lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        bottomSheetView = inflater.inflate(
            R.layout.fragment_filter_recipe_bottomsheet, container, false
        )
        val mealTypeChip = bottomSheetView.findViewById<ChipGroup>(R.id.cgMealType)
        val dietTypeChip = bottomSheetView.findViewById<ChipGroup>(R.id.cgDietType)
        recipeViewModel.onEvent(
            RecipeEvent.PersistSelectedChips(
                mealTypeChip, dietTypeChip
            )
        )

        applyFilter(mealTypeChip, dietTypeChip)
        return bottomSheetView
    }

    private fun applyFilter(mealTypeChip: ChipGroup, dietTypeChip: ChipGroup) {
        var mealType = "main course"
        var dietType = "vegan"
        var mealTypeChipId = 0
        var dietTypeChipId = 0
        val applyBtn = bottomSheetView.findViewById<Button>(R.id.btnApply)
        val etSearchRecipe = bottomSheetView.findViewById<EditText>(R.id.etSearchRecipe)
        mealTypeChip.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = mealTypeChip.findViewById<Chip>(selectedChipId)
            mealType = chip.text.toString().toLowerCase(Locale.ROOT)
            mealTypeChipId = selectedChipId
        }
        dietTypeChip.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = dietTypeChip.findViewById<Chip>(selectedChipId)
            dietType = chip.text.toString().toLowerCase(Locale.ROOT)
            dietTypeChipId = selectedChipId
        }
        applyBtn.setOnClickListener {
            recipeViewModel.onEvent(
                RecipeEvent.SelectRecipesFilter(
                    dietType = dietType,
                    mealType = mealType,
                    mealTypeChipId = mealTypeChipId,
                    dietTypeChipId = dietTypeChipId,
                    query = etSearchRecipe.text.toString(),
                )
            )
            findNavController().navigate(R.id.action_filterRecipeBottomsheet2_to_recipesFragment)
        }
    }
}