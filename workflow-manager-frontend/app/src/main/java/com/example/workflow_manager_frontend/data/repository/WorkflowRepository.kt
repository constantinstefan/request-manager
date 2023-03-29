package com.example.workflow_manager_frontend.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workflow_manager_frontend.domain.model.Workflow
import kotlinx.coroutines.flow.Flow

interface WorkflowRepository {
    fun getWorkflows(fetchFromRemote : Boolean): List<Workflow>

    fun getWorkflowById(id: Int, fetchFromRemote: Boolean) : Workflow?

    fun insertWorkflow(workflow: Workflow)

    fun deleteWorkflow(workflow: Workflow)
}