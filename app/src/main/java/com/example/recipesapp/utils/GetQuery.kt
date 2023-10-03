package com.example.recipesapp.utils

import com.example.recipesapp.utils.models.QueryRequestModel

object GetQuery {
    fun getQueryMap(queryRequestModel: QueryRequestModel): Map<String, Any> {
        val queryMap = mutableMapOf<String, Any>(
            "number" to queryRequestModel.number, "offset" to queryRequestModel.offset
        )
        if (queryRequestModel.diet != null) {
            queryMap["diet"] = queryRequestModel.diet
        }
        if (queryRequestModel.type != null) {
            queryMap["type"] = queryRequestModel.type
        }
        return queryMap;
    }
}