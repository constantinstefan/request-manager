package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatGptStep(
    var outputVariable: String ="",
    var prompt: String= ""
) : Parcelable