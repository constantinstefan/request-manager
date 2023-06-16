package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.Execution
import com.google.gson.Gson

object ExecutionConverter {
    @TypeConverter
    fun fromString(value: String?): Execution? {
        return value?.let { Gson().fromJson(it, Execution::class.java) }
    }

    @TypeConverter
    fun toString(value: Execution?): String? {
        return value?.let { Gson().toJson(it) }
    }
}