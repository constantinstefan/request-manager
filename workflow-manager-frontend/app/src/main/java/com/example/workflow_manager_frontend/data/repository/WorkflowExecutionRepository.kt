package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext

interface WorkflowExecutionRepository {
    suspend fun doExecution(workflowId: Long, workflowExecutionContext: WorkflowExecutionContext)
}