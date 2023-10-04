package com.example.recipesapp.features.data.models.recipes

import com.google.gson.annotations.SerializedName

data class RecipeResponseModel(
    @SerializedName("status") val status: String?,
    @SerializedName("code") val code: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("number") val number: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("results") val results: List<Recipe?>?,
    @SerializedName("totalResults") val totalResults: Int?
) {
    data class Recipe(
        @SerializedName("id") val id: Int?,
        @SerializedName("image") val image: String?,
        @SerializedName("imageType") val imageType: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("vegan") val Vegan: Boolean?,
        @SerializedName("readyInMinutes") val readyInMinutes: Int?,
        @SerializedName("summary") val summary: String?,
        @SerializedName("aggregateLikes") val aggregateLikes: Int?
    )
}