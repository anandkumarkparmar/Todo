package com.anandparmar.todo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Query("UPDATE tasks SET task_completed = :taskCompleted WHERE task_id = :taskId")
    fun completeATask(taskId: Long, taskCompleted: Boolean = true)

    @Query("SELECT * FROM tasks")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE task_completed = :taskCompleted")
    fun getAll(taskCompleted: Boolean): LiveData<List<Task>>
}