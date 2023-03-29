package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.model.Workflow

class WorkflowRepositoryStub : WorkflowRepository {

    private var workflowList : List<Workflow> =  mutableListOf(
        Workflow(1, "Cerere buletin" , "public", null, null),
        Workflow(2, "Cerere pasaport" , "public", null, null)
    )

    override fun getWorkflows(fetchFromRemote: Boolean): List<Workflow>
        = workflowList

    override fun getWorkflowById(id: Int, fetchFromRemote: Boolean): Workflow?
        = workflowList[id]

    override fun insertWorkflow(workflow: Workflow) {
        //workflowList.add(workflow)
    }

    override fun deleteWorkflow(workflow: Workflow) {
        //workflowList.remove(workflow)
    }
}