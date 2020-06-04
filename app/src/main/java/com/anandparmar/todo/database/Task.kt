package com.anandparmar.todo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    var taskId: Long = 0L,

    @ColumnInfo(name = "notify_time_milli")
    var notifyTimeInMillis: Long? = null,

    @ColumnInfo(name = "task_completed")
    var taskCompleted: Boolean = false
)