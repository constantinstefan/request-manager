package com.example.workflow_manager_frontend.domain

data class Sharing(
    val groupId: Int,
    val id: Int,
    val sharingType: String,
    val workflowId: Int
)