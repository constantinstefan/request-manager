package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Sharing
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.request.SharingRequest
import com.example.workflow_manager_frontend.domain.request.WorkflowRequest


class WorkflowRepositoryStub : WorkflowRepository {

    private var workflowList : List<Workflow> =  mutableListOf(
        Workflow("Cerere buletin", 1,"Cerere buletin" , Sharing(0,0,"public",1)),
        Workflow("Cerere pasaport", 2,"Cerere pasaport" , Sharing(0,0,"public", 2))
    )

    override suspend fun getWorkflows(fetchFromRemote: Boolean): List<Workflow>
        = workflowList

    override suspend fun getWorkflowById(id: Int, fetchFromRemote: Boolean): Workflow?
        = workflowList[id]

    override suspend fun insertWorkflow(workflow: Workflow) {
        //workflowList.add(workflow)
    }

    override suspend fun deleteWorkflow(workflow: Workflow) {
        //workflowList.remove(workflow)
    }

    override suspend fun setSharingRemote(workflowId: Int?, sharing: SharingRequest): Sharing? {
        TODO("Not yet implemented")
    }

    override suspend fun setWorkflowRemote(workflow: WorkflowRequest): Workflow? {
        TODO("Not yet implemented")
    }
}