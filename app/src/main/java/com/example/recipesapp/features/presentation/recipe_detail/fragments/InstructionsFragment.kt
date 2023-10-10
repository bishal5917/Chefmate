package com.example.recipesapp.features.presentation.recipe_detail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.recipesapp.databinding.FragmentInstructionsBinding
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailState
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailViewmodel
import com.example.recipesapp.utils.CustomToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InstructionsFragment : Fragment() {

    @Inject
    lateinit var recipeDetailViewModel: RecipeDetailViewmodel

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData() {
        val progressBar = binding.pbInstructions
        recipeDetailViewModel.recipeDetailState.observe(viewLifecycleOwner) { response ->
            if (response.status == RecipeDetailState.RecipeDetailStatus.LOADING) {
                progressBar.visibility = View.VISIBLE
            }
            if (response.status == RecipeDetailState.RecipeDetailStatus.SUCCESS) {
                progressBar.visibility = View.GONE
                binding.wvInstructions.webViewClient = object : WebViewClient() {}
                val websiteUrl: String = response.recipeDetail?.sourceUrl ?: ""
                binding.wvInstructions.loadUrl(websiteUrl)
            }
            if (response.status == RecipeDetailState.RecipeDetailStatus.FAILED) {
                progressBar.visibility = View.GONE
                CustomToast.showToast(context = requireContext(), "${response.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}