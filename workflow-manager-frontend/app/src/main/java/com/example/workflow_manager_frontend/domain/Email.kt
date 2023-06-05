package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Email (
    var receiver: String = "",
    var subject: String = "",
    var content: String = "",
    var attachments: String = ""
    ) : Parcelable