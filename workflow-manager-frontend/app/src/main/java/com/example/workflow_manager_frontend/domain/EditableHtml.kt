package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditableHtml(
    var content: String = "",
    val pdfResultVariable: String = "",
    val uploadedEditedHtmlFileVariable: String = "",
    var fileName: String=""
) : Parcelable