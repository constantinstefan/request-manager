package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormField(
    val id: Int = 0,
    var label: String = "",
    var name: String = "",
    var isRequired: Boolean = false
) : Parcelable