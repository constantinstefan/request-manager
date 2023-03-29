package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.data.source.WorkflowDao
import com.example.workflow_manager_frontend.domain.model.Workflow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkflowRepositoryImpl @Inject constructor(
        private val workflowDao: WorkflowDao)
    : WorkflowRepository {

    override fun getWorkflows(fetchFromRemote : Boolean): List<Workflow> {
        return workflowDao.getWorkflows()
    }

    override fun getWorkflowById(id: Int, fetchFromRemote: Boolean): Workflow? {
        return workflowDao.getWorkflowById(id)
    }

    override fun insertWorkflow(workflow: Workflow) {
        return workflowDao.insertWorkflow(workflow)
    }

    override fun deleteWorkflow(workflow: Workflow) {
        return workflowDao.deleteWorkflow(workflow)
    }
}