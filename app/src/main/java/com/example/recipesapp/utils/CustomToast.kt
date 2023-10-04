package com.example.recipesapp.utils

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

class CustomToast {
    companion object {
        fun showToast(message: String, context: Context) {
            Toast.makeText(context, message ?: "Message", Toast.LENGTH_SHORT)
                .show()
        }
    }
}