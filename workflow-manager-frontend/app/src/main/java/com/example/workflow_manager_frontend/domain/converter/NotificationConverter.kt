package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.Notification
import com.google.gson.Gson

object NotificationConverter {
    @TypeConverter
    fun fromString(value: String?): Notification? {
        return value?.let { Gson().fromJson(it, Notification::class.java) }
    }

    @TypeConverter
    fun toString(value: Notification?): String? {
        return value?.let { Gson().toJson(it) }
    }
}