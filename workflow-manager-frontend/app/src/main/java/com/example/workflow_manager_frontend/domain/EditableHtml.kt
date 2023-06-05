package com.example.workflow_manager_frontend.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class EditableHtml(
    var content: String = "",
    var pdfResultVariable: String = "",
    var uploadedEditedHtmlFileVariable: String = "",
    var fileName: String="",
    var isRequired: Boolean = true,
    var file: File? = null
) : Parcelable