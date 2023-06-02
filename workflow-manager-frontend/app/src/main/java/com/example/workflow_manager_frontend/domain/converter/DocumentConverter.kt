package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.Document
import com.example.workflow_manager_frontend.domain.Sharing
import com.google.gson.Gson

object DocumentConverter {
    @TypeConverter
    fun fromString(value: String?): Document? {
        return value?.let { Gson().fromJson(it, Document::class.java) }
    }

    @TypeConverter
    fun toString(value: Document?): String? {
        return value?.let { Gson().toJson(it) }
    }
}