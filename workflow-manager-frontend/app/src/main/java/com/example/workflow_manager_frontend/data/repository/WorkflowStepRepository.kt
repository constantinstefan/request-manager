package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.WorkflowStep

interface WorkflowStepRepository {
    suspend fun getWorkflowSteps(workflowId: Long, fetchFromRemote: Boolean) : List<WorkflowStep>?

    suspend fun createWorkflowStep(workflowId: Int?, step: WorkflowStep)

    suspend fun updateWorkflowStep(workflowId: Long, step: WorkflowStep)
}