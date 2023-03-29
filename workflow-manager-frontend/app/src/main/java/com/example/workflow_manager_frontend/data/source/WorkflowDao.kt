package com.example.workflow_manager_frontend.data.source

import androidx.room.*
import com.example.workflow_manager_frontend.domain.model.Workflow

@Dao
interface WorkflowDao {
    @Query("SELECT * FROM workflow")
    fun getWorkflows(): List<Workflow>

    @Query("SELECT * FROM workflow where id = :id")
    fun getWorkflowById(id: Int) : Workflow?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWorkflow(workflow: Workflow)

    @Delete
    fun deleteWorkflow(workflow: Workflow)
}