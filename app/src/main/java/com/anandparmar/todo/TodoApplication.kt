package com.anandparmar.todo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        createChannel(
            getString(R.string.task_notification_channel_id),
            getString(R.string.task_notification_channel_name),
            getString(R.string.task_notification_channel_description)
        )
    }

    private fun createChannel(channelId: String, channelName: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationChannel.description = description

            val notificationManager = applicationContext.getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }
}