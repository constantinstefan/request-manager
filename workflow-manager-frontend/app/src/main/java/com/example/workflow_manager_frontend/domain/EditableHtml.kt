package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditableHtml(
    val content: String,
    val pdfResultVariable: String,
    val uploadedEditedHtmlFileVariable: String
) : Parcelable