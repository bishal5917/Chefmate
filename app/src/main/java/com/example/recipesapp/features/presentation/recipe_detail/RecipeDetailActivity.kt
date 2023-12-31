package com.example.recipesapp.features.presentation.recipe_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.recipesapp.R
import com.example.recipesapp.features.data.models.recipe_detail.RecipeRequestModel
import com.example.recipesapp.features.presentation.recipe_detail.adapters.PagerAdapter
import com.example.recipesapp.features.presentation.recipe_detail.fragments.IngredientsFragment
import com.example.recipesapp.features.presentation.recipe_detail.fragments.InstructionsFragment
import com.example.recipesapp.features.presentation.recipe_detail.fragments.OverviewFragment
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailEvent
import com.example.recipesapp.features.presentation.recipe_detail.viewmodel.RecipeDetailViewmodel
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeEvent
import com.example.recipesapp.features.presentation.recipes.viewmodel.RecipeViewModel
import com.example.recipesapp.utils.models.QueryRequestModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var recipeDetailViewModel: RecipeDetailViewmodel

    //getting argument (id)
    private val args by navArgs<RecipeDetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val toolbar = findViewById<Toolbar>(R.id.tbRecipeDetail)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTabs()
        requestApi()
    }

    private fun requestApi() {
        recipeDetailViewModel.onEvent(
            RecipeDetailEvent.GetRecipeDetail(
                RecipeRequestModel
                    (recipeId = args.id)
            )
        )
    }

    private fun setTabs() {
        val fragments = ArrayList<Fragment>()
        val titles = ArrayList<String>()
        val resultBundle = Bundle()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")
        resultBundle.putInt("recipeId", 654905)
        val adapter = PagerAdapter(resultBundle, fragments, titles, supportFragmentManager)
        val recipesTab = findViewById<TabLayout>(R.id.tlRecipesTab)
        val recipesPager = findViewById<ViewPager>(R.id.vpRecipeDetail)
        recipesPager.adapter = adapter
        recipesTab.setupWithViewPager(recipesPager)
    }

    //going back to previous screen when back arrow is pressed
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}