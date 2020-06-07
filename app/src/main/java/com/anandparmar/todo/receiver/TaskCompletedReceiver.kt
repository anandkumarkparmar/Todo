package com.anandparmar.todo.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.anandparmar.todo.database.TodoDatabase
import com.anandparmar.todo.utils.TASK_ID_EXTRAS
import com.anandparmar.todo.utils.cancelNotifications
import kotlinx.coroutines.*

class TaskCompletedReceiver: BroadcastReceiver() {
    private var receiverJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + receiverJob)

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra(TASK_ID_EXTRAS, -1L)
        if (taskId != -1L) {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    TodoDatabase.getInstance(context).taskDao.completeATask(taskId)
                    val notificationManager = ContextCompat.getSystemService(
                        context,
                        NotificationManager::class.java
                    ) as NotificationManager
                    notificationManager.cancel(taskId.toInt())
                }
            }
        }
    }

}