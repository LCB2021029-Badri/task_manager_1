package com.example.taskmanager.interfaces

import com.example.taskmanager.data.models.Task

interface OnTaskClickListener {
    fun onTaskClick(task: Task)
    fun onTaskLongClick(task: Task)
    fun onTaskCompletionChanged(task: Task)
}