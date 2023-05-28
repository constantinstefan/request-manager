package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.data.source.db.WorkflowDao
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Workflow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkflowRepositoryImpl @Inject constructor(
        private val workflowDao: WorkflowDao
)
    : WorkflowRepository {

    override suspend fun getWorkflows(fetchFromRemote : Boolean): List<Workflow> {
        if(fetchFromRemote) {
            val principalResponse = RetrofitInstance.api.getPrincipal()
            if (!principalResponse.isSuccessful) {
                return emptyList()
            }

            val workflowResponse = principalResponse.body()?.let { principal ->
                RetrofitInstance.api.getWorkflows(principal.id)
            }

            if (workflowResponse != null && !workflowResponse.isSuccessful) {
                return emptyList()
            }

            return workflowResponse?.body().orEmpty()
        }
        return workflowDao.getWorkflows();
    }

    override suspend fun getWorkflowById(id: Int, fetchFromRemote: Boolean): Workflow? {
        return workflowDao.getWorkflowById(id)
    }

    override suspend fun insertWorkflow(workflow: Workflow) {
        return workflowDao.insertWorkflow(workflow)
    }

    override suspend fun deleteWorkflow(workflow: Workflow) {
        return workflowDao.deleteWorkflow(workflow)
    }
}