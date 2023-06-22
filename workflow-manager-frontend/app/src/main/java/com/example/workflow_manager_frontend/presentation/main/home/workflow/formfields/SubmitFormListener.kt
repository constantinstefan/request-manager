package com.example.workflow_manager_frontend.presentation.main.home.workflow.formfields

import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext

interface SubmitFormListener {
    fun onFieldError(position: Int, errorMessage: String)
    fun onSubmitForm(workflowExecutionContext: WorkflowExecutionContext)
}