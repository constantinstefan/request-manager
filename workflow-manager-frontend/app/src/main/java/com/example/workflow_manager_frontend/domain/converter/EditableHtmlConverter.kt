package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.EditableHtml
import com.example.workflow_manager_frontend.domain.Sharing
import com.google.gson.Gson

object EditableHtmlConverter {
    @TypeConverter
    fun fromString(value: String?): EditableHtml? {
        return value?.let { Gson().fromJson(it, EditableHtml::class.java) }
    }

    @TypeConverter
    fun toString(value: EditableHtml?): String? {
        return value?.let { Gson().toJson(it) }
    }
}