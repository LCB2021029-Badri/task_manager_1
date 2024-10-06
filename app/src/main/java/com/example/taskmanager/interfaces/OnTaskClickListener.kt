package com.example.taskmanager.interfaces

import com.example.taskmanager.data.models.Task

interface OnTaskClickListener {
    fun onTaskClick(task: Task)
}