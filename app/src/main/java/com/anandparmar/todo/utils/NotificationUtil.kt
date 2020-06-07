package com.anandparmar.todo.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.anandparmar.todo.R
import com.anandparmar.todo.database.Task
import com.anandparmar.todo.receiver.TaskCompletedReceiver
import com.anandparmar.todo.ui.LoginActivity

const val TASK_ID_EXTRAS = "TASK_ID"

fun NotificationManager.sendNotification(task: Task, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, LoginActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        task.taskId.toInt(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val taskCompletedIntent = Intent(applicationContext, TaskCompletedReceiver::class.java)
    taskCompletedIntent.putExtra(TASK_ID_EXTRAS, task.taskId)
    val taskCompletedPendingIntent: PendingIntent = PendingIntent.getBroadcast(
        applicationContext,
        task.taskId.toInt(),
        taskCompletedIntent,
        0)

    val notification = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.task_notification_channel_id))
        .setSmallIcon(R.drawable.ic_check_box_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(task.taskDetails)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .addAction(
            R.drawable.ic_check_circle_black_24dp,
            applicationContext.getString(R.string.task_completed),
            taskCompletedPendingIntent
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH).build()

    notify(task.taskId.toInt(), notification)
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}