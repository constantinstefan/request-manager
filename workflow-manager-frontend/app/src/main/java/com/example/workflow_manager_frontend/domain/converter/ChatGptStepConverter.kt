package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.ChatGptStep
import com.google.gson.Gson

object ChatGptStepConverter {
    @TypeConverter
    fun fromString(value: String?): ChatGptStep? {
        return value?.let { Gson().fromJson(it, ChatGptStep::class.java) }
    }

    @TypeConverter
    fun toString(value: ChatGptStep?): String? {
        return value?.let { Gson().toJson(it) }
    }
}