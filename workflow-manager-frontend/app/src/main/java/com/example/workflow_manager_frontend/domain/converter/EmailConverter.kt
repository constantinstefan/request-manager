package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.Email
import com.google.gson.Gson

object EmailConverter {
    @TypeConverter
    fun fromString(value: String?): Email? {
        return value?.let { Gson().fromJson(it, Email::class.java) }
    }

    @TypeConverter
    fun toString(value: Email?): String? {
        return value?.let { Gson().toJson(it) }
    }
}