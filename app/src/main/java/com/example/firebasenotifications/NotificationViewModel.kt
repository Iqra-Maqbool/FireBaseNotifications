package com.example.firebasenotifications

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebasenotifications.domain.constants.NotificationConstants
import com.google.firebase.messaging.FirebaseMessaging

class NotificationViewModel: ViewModel() {

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchFirebaseToken()
    }

    private fun fetchFirebaseToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(
                    NotificationConstants.KEY_TOKEN_DETAIL,
                    NotificationConstants.KEY_FAILED_TO_RECEIVE_TOKEN
                )
                _error.value = NotificationConstants.KEY_FAILED_TO_RECEIVE_TOKEN
                return@addOnCompleteListener
            }
            val token = task.result
            _fcmToken.value = token ?: NotificationConstants.KEY_NO_TOKEN_RECEIVED
        }
    }
}
