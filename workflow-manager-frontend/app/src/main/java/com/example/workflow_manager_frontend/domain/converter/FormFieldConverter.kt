package com.example.workflow_manager_frontend.domain.converter

import androidx.room.TypeConverter
import com.example.workflow_manager_frontend.domain.EditableHtml
import com.example.workflow_manager_frontend.domain.FormField
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FormFieldConverter {

    @TypeConverter
    fun fromString(value: String?): List<FormField>? {
        if (value == null) {
            return null
        }

        val listType = object : TypeToken<List<FormField>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(formFields: List<FormField>?): String? {
        return Gson().toJson(formFields)
    }
}