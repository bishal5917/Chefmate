package com.example.recipesapp.utils.models

data class QueryRequestModel(
    val number: String = "6",
    val offset: String = "0",
    val diet: String? = null,
    val type: String? = null,
)