package com.example.firebasenotifications.data

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firebasenotifications.MainActivity
import com.example.firebasenotifications.R
import com.example.firebasenotifications.domain.constants.NotificationConstants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FireBaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            showNotification(
                it.title ?: NotificationConstants.KEY_NO_TITLE,
                it.body ?: NotificationConstants.KEY_NO_MSG_BODY
            )
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = NotificationConstants.KEY_FIREBASE_CHANNEL
        val notificationId = System.currentTimeMillis().toInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                NotificationConstants.KEY_FIREBASE_NOTIFICATIONS,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = NotificationConstants.KEY_CHANNELS_FOR_FIREBASE_NOTIFICATION
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this).notify(notificationId, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(NotificationConstants.KEY_REFRESHED_TOKEN, token)
    }
}
