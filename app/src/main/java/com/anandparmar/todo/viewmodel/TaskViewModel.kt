package com.anandparmar.todo.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.AndroidViewModel
import com.anandparmar.todo.database.Task
import com.anandparmar.todo.database.TodoDatabase
import com.anandparmar.todo.receiver.AlarmReceiver
import com.anandparmar.todo.repository.TaskRepository
import com.anandparmar.todo.utils.FirebaseUserLiveData
import com.anandparmar.todo.utils.TASK_ID_EXTRAS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TaskViewModel(private val app: Application) : AndroidViewModel(app) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val repository = TaskRepository(TodoDatabase.getInstance(app))
    val nonCompletedTasks = TodoDatabase.getInstance(app).taskDao.getAll(false)
    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val authenticationState = FirebaseUserLiveData()

    fun saveTask(task: Task) {
        uiScope.launch {
            val taskId = repository.insertTask(task)
            task.notifyTimeInMillis?.let {
                scheduleAlarm(taskId, it)
            }
        }
    }

    private fun scheduleAlarm(taskId: Long, notifyTimeInMillis: Long) {
        val notifyIntent = Intent(app, AlarmReceiver::class.java)
        notifyIntent.putExtra(TASK_ID_EXTRAS, taskId)

        val notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            taskId.toInt(),
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            notifyTimeInMillis,
            notifyPendingIntent
        )
    }

    fun completeATask(taskId: Long) {
        uiScope.launch {
            repository.completeATask(taskId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}