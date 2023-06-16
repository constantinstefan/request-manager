package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.WorkflowStep

interface WorkflowStepRepository {
    suspend fun getWorkflowSteps(workflowId: Int?, fetchFromRemote: Boolean) : List<WorkflowStep>?

    suspend fun createWorkflowStep(workflowId: Int?, step: WorkflowStep)

    suspend fun updateWorkflowStep(workflowId: Int?, step: WorkflowStep)
}