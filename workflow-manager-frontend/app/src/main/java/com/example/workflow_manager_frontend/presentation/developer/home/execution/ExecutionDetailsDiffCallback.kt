package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.workflow_manager_frontend.domain.StepExecution
import com.example.workflow_manager_frontend.domain.WorkflowStep

class ExecutionDetailsDiffCallback: DiffUtil.ItemCallback<StepExecution>() {
    override fun areItemsTheSame(oldItem: StepExecution, newItem: StepExecution): Boolean {
        return oldItem.step.workflowStepId == newItem.step.workflowStepId
    }

    override fun areContentsTheSame(oldItem: StepExecution, newItem: StepExecution): Boolean {
        return (oldItem.execution.stepNumber == newItem.execution.stepNumber)
                && (oldItem.execution.status == newItem.execution.status)
    }
}