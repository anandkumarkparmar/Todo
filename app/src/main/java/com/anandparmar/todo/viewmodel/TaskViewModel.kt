package com.anandparmar.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anandparmar.todo.database.Task
import com.anandparmar.todo.database.TodoDatabase
import com.anandparmar.todo.repository.TaskRepository
import com.anandparmar.todo.utils.FirebaseUserLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val repository = TaskRepository(TodoDatabase.getInstance(application))
    val nonCompletedTasks = TodoDatabase.getInstance(application).taskDao.getAll(false)

    val authenticationState = FirebaseUserLiveData()

    fun saveTask(task: Task) {
        uiScope.launch {
            repository.insertTask(task)
        }
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