package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Sharing
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.request.SharingRequest
import com.example.workflow_manager_frontend.domain.request.WorkflowRequest

interface WorkflowRepository {
    suspend fun getWorkflows(fetchFromRemote : Boolean): List<Workflow>

    suspend fun getWorkflowById(id: Int, fetchFromRemote: Boolean) : Workflow?

    suspend fun insertWorkflow(workflow: Workflow)

    suspend fun deleteWorkflow(workflow: Workflow)

    suspend fun setSharingRemote(workflowId: Int?, sharing: SharingRequest) : Sharing?

    suspend fun setWorkflowRemote(workflow: WorkflowRequest) : Workflow?
}