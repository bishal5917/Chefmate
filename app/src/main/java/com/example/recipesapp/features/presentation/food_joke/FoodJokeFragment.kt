package com.example.recipesapp.features.presentation.food_joke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipesapp.R
import com.example.recipesapp.databinding.FragmentFoodJokeBinding
import com.example.recipesapp.databinding.FragmentOverviewBinding
import com.example.recipesapp.features.presentation.food_joke.viewmodel.JokeEvent
import com.example.recipesapp.features.presentation.food_joke.viewmodel.JokeState
import com.example.recipesapp.features.presentation.food_joke.viewmodel.JokeViewModel
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailState
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeEvent
import com.example.recipesapp.utils.CustomToast
import com.example.recipesapp.utils.models.QueryRequestModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    @Inject
    lateinit var jokeViewModel: JokeViewModel

    private lateinit var binding: FragmentFoodJokeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout with data binding
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_food_joke, container, false
        )
        observeLiveData()
        pullToRefresh()
        return binding.root
    }

    private fun pullToRefresh() {
        val swipeToRefresh = binding.srRefreshJoke
        swipeToRefresh.setOnRefreshListener {
            jokeViewModel.onEvent(JokeEvent.GetJoke)
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun observeLiveData() {
        jokeViewModel.jokeState.observe(viewLifecycleOwner) { response ->
            if (response.status == JokeState.JokeStatus.IDLE) {
                //call joke fetching api
                jokeViewModel.onEvent(JokeEvent.GetJoke)
            }
            if (response.status == JokeState.JokeStatus.LOADING) {
                binding.tvFoodJoke.text = ". . ."
            }
            if (response.status == JokeState.JokeStatus.SUCCESS) {
                binding.result = response.joke
            }
            if (response.status == JokeState.JokeStatus.FAILED) {
                CustomToast.showToast(context = requireContext(), "${response.message}")
            }
        }
    }
}