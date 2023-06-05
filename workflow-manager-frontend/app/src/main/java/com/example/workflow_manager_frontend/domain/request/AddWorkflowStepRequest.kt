package com.example.workflow_manager_frontend.domain.request

data class AddWorkflowStepRequest(
    val stepNumber: Int,
    val stepType: String
)