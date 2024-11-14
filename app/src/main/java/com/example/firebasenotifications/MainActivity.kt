package com.example.firebasenotifications

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasenotifications.databinding.ActivityMainBinding
import com.example.firebasenotifications.domain.constants.NotificationConstants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
    }

    private fun observers() {
        notificationViewModel.fcmToken.observe(this) { token ->
            Log.d(NotificationConstants.KEY_FCM_TOKEN, token)
        }

        notificationViewModel.error.observe(this) { errorMessage ->
            Log.d(NotificationConstants.KEY_TOKEN_DETAIL, errorMessage)
        }
    }
}
