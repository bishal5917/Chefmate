package com.example.recipesapp

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.auth.api.identity.Identity

class GoogleOneTapActivity : AppCompatActivity() {

    private var TAG: String = "GOOGLE";
    private var oneTapClient: SignInClient? = null
    private var signUpRequest: BeginSignInRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneTapClient = Identity.getSignInClient(this)
        signUpRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId("HAHA")
                // Show all accounts on the device.
                .setFilterByAuthorizedAccounts(false).build()
        ).build()

//        val btn = findViewById<Button>(R.id.btnGoogleSignIn)

//        btn.setOnClickListener {
//            Log.d(TAG, "I am clicked")
//            displaySignUp()
//        }
    }

    private val oneTapResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
                val idToken = credential?.googleIdToken
                when {
                    idToken != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        val msg = "idToken: $idToken"
                        Log.d(TAG, msg)
                    }

                    else -> {
                        // Shouldn't happen.
                        Log.d(TAG, "No ID token!")
                    }
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        Log.d(TAG, "One-tap dialog was closed.")
                    }

                    CommonStatusCodes.NETWORK_ERROR -> {
                        Log.d(TAG, "One-tap encountered a network error.")
                    }

                    else -> {
                        Log.d(
                            TAG, "Couldn't get credential from result." + " (${e.localizedMessage})"
                        )
                    }
                }
            }
        }

    private fun displaySignUp() {
        oneTapClient?.beginSignIn(signUpRequest!!)?.addOnSuccessListener(this) { result ->
            try {
                val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                oneTapResult.launch(ib)
            } catch (e: IntentSender.SendIntentException) {
                Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
            }
        }?.addOnFailureListener(this) { e ->
            // No Google Accounts found. Just continue presenting the signed-out UI.
            Log.d(TAG, e.localizedMessage!!)
        }
    }
}