package com.anandparmar.todo.repository

import androidx.lifecycle.LiveData
import com.anandparmar.todo.database.Task
import com.anandparmar.todo.database.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val database: TodoDatabase) {

    suspend fun insertTask(task: Task): Long {
        return withContext(Dispatchers.IO) {
            database.taskDao.insert(task)
        }
    }

    suspend fun getAllTasks(taskCompleted: Boolean): LiveData<List<Task>> {
        return withContext(Dispatchers.IO) {
           database.taskDao.getAll(taskCompleted)
        }
    }

    suspend fun completeATask(taskId: Long) {
        withContext(Dispatchers.IO) {
            database.taskDao.completeATask(taskId)
        }
    }
}