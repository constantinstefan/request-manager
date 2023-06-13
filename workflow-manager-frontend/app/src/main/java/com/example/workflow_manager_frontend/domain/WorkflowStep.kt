package com.example.workflow_manager_frontend.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkflowStep(
    val document: Document? = null,
    val editableHtml: EditableHtml? = null,
    val formFields: MutableList<FormField>? = null,
    val email: Email? = null,
    val chatGptStep: ChatGptStep? = null,
    val stepDescription: String = "",
    val stepName: String = "",
    var stepNumber: Int = 0,
    val stepType: String = "",
    val workflowId: Int = 0,
    @PrimaryKey val workflowStepId: Int = 0
)