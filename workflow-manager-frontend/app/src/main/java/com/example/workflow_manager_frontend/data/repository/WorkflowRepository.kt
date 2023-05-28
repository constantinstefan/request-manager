package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Workflow

interface WorkflowRepository {
    suspend fun getWorkflows(fetchFromRemote : Boolean): List<Workflow>

    suspend fun getWorkflowById(id: Int, fetchFromRemote: Boolean) : Workflow?

    suspend fun insertWorkflow(workflow: Workflow)

    suspend fun deleteWorkflow(workflow: Workflow)
}