package com.example.workflow_manager_frontend.domain

data class StepExecution(
    val step: WorkflowStep,
    var execution: Execution
) {
}