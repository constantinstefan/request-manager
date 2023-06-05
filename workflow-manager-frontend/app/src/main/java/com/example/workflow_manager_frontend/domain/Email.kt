package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Email (
    val receiver: String = "",
    val subject: String = "",
    val content: String = "",
    val attachments: String = ""
    ) : Parcelable