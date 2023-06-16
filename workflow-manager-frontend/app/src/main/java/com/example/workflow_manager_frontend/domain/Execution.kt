package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Execution(
    val id: Int,
    val startTime: Long?,
    val endTime: Long?,
    val status: String,
    val message: String?,
    val stepNumber: Int,
    val workflowId: Int,
    val customer: Customer?
) : Parcelable