package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormField(
    val id: Int,
    val label: String
) : Parcelable