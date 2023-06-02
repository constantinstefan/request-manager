package com.example.workflow_manager_frontend.data.source.db

import androidx.room.Dao
import androidx.room.Query
import com.example.workflow_manager_frontend.domain.WorkflowStep

@Dao
interface WorkflowStepDao {

    @Query("SELECT * FROM workflowstep where workflowstep.workflowId = :workflowId")
    fun getWorkflowStepsByWorkflowId(workflowId: Long) : List<WorkflowStep>
}