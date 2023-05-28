package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.Sharing
import com.google.gson.Gson

object SharingTypeConverter {
    @TypeConverter
    fun fromString(value: String?): Sharing? {
        return value?.let { Gson().fromJson(it, Sharing::class.java) }
    }

    @TypeConverter
    fun toString(value: Sharing?): String? {
        return value?.let { Gson().toJson(it) }
    }
}