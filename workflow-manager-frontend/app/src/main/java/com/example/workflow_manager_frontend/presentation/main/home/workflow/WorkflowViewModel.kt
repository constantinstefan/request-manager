package com.example.workflow_manager_frontend.presentation.main.home.workflow

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.data.repository.WorkflowExecutionRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowStepRepository
import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext
import com.example.workflow_manager_frontend.domain.WorkflowStep
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkflowViewModel @Inject constructor(
    private val workflowStepRepository: WorkflowStepRepository,
    private val workflowExecutionRepository: WorkflowExecutionRepository
)
    : ViewModel() {

    private val tag = "WorkflowViewModel"

    suspend fun getSteps(workflowId: Int?) : List<WorkflowStep>? {
        val steps = workflowStepRepository.getWorkflowSteps(workflowId, true)
        Log.d(tag, steps.toString())
        return steps
    }

    suspend fun executeWorkflow(workflowId : Long, workflowExecutionContext: WorkflowExecutionContext) {
        workflowExecutionRepository.doExecution(workflowId, workflowExecutionContext)
    }
}