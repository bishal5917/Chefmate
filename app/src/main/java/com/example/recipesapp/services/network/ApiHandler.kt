package com.example.recipesapp.services.network

import android.content.Context
import android.util.Log
import com.example.recipesapp.core.configs.ApiConfig
import com.example.recipesapp.core.errors.ApiExceptions
import com.example.recipesapp.core.errors.ApiNotRespondingException
import com.example.recipesapp.core.errors.BadRequestException
import com.example.recipesapp.core.errors.FetchDataException
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.wait
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ApiHandler {
    //getting the user token
    private val token = "token"
    private val apiKey = ApiConfig.apiKey
    private val baseUrl = ApiConfig.baseUrl
    private val gson = Gson()
    private val okHttpClient = OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS).build()

    // GET request
    fun get(api: String, header: Map<String, String>? = null, isAuth: Boolean = false): Any? {
//        val token = if (isAuth) SharedPreference.getToken(context) else null
        println("CALLED IN GET")

        val request = Request.Builder().url(baseUrl + api).apply {
            header?.forEach { (key, value) ->
                addHeader(key, value)
            }
            if (apiKey.isNotEmpty()) {
//                addHeader("Authorization", "Bearer $token")
                addHeader("x-api-key", apiKey)
            }
            get()
        }.build()
        try {
            println("CALLED IN TRY")
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful){
                println("success")
            }
            println("$baseUrl$api")
            Log.d("API", "$baseUrl$api")
            Log.d("APIStatus", "${response.code}")
            Log.d("APIResponse", "${response.body}")
            return processResponse(response)
        } catch (ex: Exception) {
            throw handleException(ex)
            println(ex)
        }
    }

    // Helper function to process the HTTP response
    private fun processResponse(response: okhttp3.Response): Any? {
        val responseBody = response.body.toString()
        return when (response.code) {
            200, 201 -> gson.fromJson(responseBody, Any::class.java) // Successful response
            else -> throw ApiExceptions(response.message, "", "", response.code)
        }
    }

    // Helper function to handle exceptions
    private fun handleException(e: Exception): Exception {
        return when (e) {
            is SocketException -> FetchDataException(e.message)
            is IOException -> BadRequestException(e.message)
            is TimeoutException -> ApiNotRespondingException(e.message)
            else -> e
        }
    }
}