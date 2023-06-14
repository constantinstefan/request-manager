package com.example.workflow_manager_frontend.domain

data class Execution(
    val id: Int,
    val startTime: Long,
    val endTime: Long,
    val status: String,
    val stepNumber: Int,
    val workflowId: Int
)