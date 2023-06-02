package com.example.workflow_manager_frontend.presentation.workflow.formfields

import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext

interface SubmitFormListener {
    fun onSubmitForm(workflowExecutionContext: WorkflowExecutionContext)
}