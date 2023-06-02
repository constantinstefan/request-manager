package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document(
    val description: String,
    val id: Int,
    val ocrRequired: Boolean,
    val ocrResultVariable: String,
    val uploadedFileVariable: String
) : Parcelable