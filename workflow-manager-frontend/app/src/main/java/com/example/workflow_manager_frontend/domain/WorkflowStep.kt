package com.example.workflow_manager_frontend.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkflowStep(
    val document: Document?,
    val editableHtml: EditableHtml?,
    val formFields: List<FormField>?,
    val stepDescription: String,
    val stepName: String,
    val stepNumber: Int,
    val stepType: String,
    val workflowId: Int,
    @PrimaryKey val workflowStepId: Int
)