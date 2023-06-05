package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document(
    var description: String = "",
    val id: Int = 0,
    val ocrRequired: Boolean = false,
    val ocrResultVariable: String = "",
    var uploadedFileVariable: String = "",
    var isRequired: Boolean = true
) : Parcelable