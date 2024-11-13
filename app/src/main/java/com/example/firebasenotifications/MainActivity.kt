package com.example.firebasenotifications

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasenotifications.databinding.ActivityMainBinding
import com.example.firebasenotifications.domain.constants.NotificationConstants
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(
                    NotificationConstants.KEY_TOKEN_DETAIL,
                    NotificationConstants.KEY_FAILED_TO_RECEIVE_TOKEN
                )
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d(
                NotificationConstants.KEY_FCM_TOKEN, token ?: NotificationConstants.KEY_NO_TOKEN_RECEIVED
            )
        }
    }
}


