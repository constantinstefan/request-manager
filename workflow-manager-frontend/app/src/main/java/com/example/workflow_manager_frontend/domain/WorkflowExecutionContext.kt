package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class WorkflowExecutionContext(
    val variableMap: MutableMap<String, String>,
    val fileMap: MutableMap<String, File>
) : Parcelable