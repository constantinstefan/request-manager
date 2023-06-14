package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Execution
import com.example.workflow_manager_frontend.domain.Notification

interface ExecutionRepository {
    suspend fun getExecutions(workflowId: Long): List<Execution>?
}