package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Execution

interface ExecutionRepository {
    suspend fun getExecutions(workflowId: Int?): List<Execution>?
}