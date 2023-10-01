package com.example.recipesapp.features.domain.usecases

interface Usecase<Type, Params> {
    suspend fun call(params: Params): Type
}