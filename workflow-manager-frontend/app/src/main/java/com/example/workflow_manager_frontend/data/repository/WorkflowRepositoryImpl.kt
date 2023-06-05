package com.example.workflow_manager_frontend.data.repository

import android.util.Log
import com.example.workflow_manager_frontend.data.source.db.WorkflowDao
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Sharing
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.request.SharingRequest
import com.example.workflow_manager_frontend.domain.request.WorkflowRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkflowRepositoryImpl @Inject constructor(
        private val workflowDao: WorkflowDao
)
    : WorkflowRepository {

    private val tag = "WorkflowRepository"

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

            val workflows = workflowResponse?.body().orEmpty()
            workflows.forEach{workflow ->
                if(workflow.sharing?.sharingType=="GROUP") {
                    val groupResponse = RetrofitInstance.api.getGroupById(workflow.sharing.groupId)
                    if(! groupResponse.isSuccessful) {
                        return@forEach
                    }
                    workflow.sharing.group = groupResponse.body()!!
                }
            }
            return workflows
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


    override suspend fun setSharingRemote(workflowId: Int?, sharing: SharingRequest) : Sharing?{
        val response = RetrofitInstance.api.setWorkflowSharing(workflowId, sharing)
        if(!response.isSuccessful) {
            Log.e(tag, response.toString())
            return null
        }
        return response.body()
    }

    override suspend fun setWorkflowRemote(workflow: WorkflowRequest) : Workflow? {
        val response = RetrofitInstance.api.addWorkflow(workflow)
        if(!response.isSuccessful) {
            Log.e(tag, response.toString())
            return null
        }
        return response.body()
    }
}