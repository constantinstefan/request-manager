package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.WorkflowStep

class StepsViewModel: ViewModel() {
    private val tag = "StepsViewModel"

    private val workflow: MutableLiveData<Workflow?> = MutableLiveData()

    private val workflowSteps: MutableLiveData<MutableList<WorkflowStep>> = MutableLiveData()

    private var selectedPosition : Int = -1

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    fun getSelectedPoition() : Int {
        return selectedPosition
    }


    fun setWorkflowSteps(steps: MutableList<WorkflowStep>) {
        workflowSteps.value = steps
    }

    fun getWorkflowSteps(): LiveData<MutableList<WorkflowStep>> {
        return workflowSteps
    }

    fun getWorkflow() : MutableLiveData<Workflow?> {
        return workflow
    }

    fun setWorkflowName(name: String) {
        val currentWorkflow = workflow.value ?: Workflow()
        currentWorkflow?.name = name
        workflow.value = currentWorkflow
    }

    fun setWorkflowDescription(description: String) {
        val currentWorkflow = workflow.value ?: Workflow()
        currentWorkflow.description = description
        workflow.value = currentWorkflow
    }

    fun addItem(workflowStep: WorkflowStep) {
        val currentSteps = workflowSteps.value.orEmpty().toMutableList()
        currentSteps.add(workflowStep)
        workflowSteps.value = currentSteps
    }

    fun getItem(position: Int): WorkflowStep? {
        return workflowSteps.value?.get(position)
    }

    fun deleteStep(position: Int) {
        val currentSteps = workflowSteps.value.orEmpty().toMutableList()
        if( (! (position in 0 until currentSteps.size)) ) {
            Log.w(tag, "out of bound position in delteStep")
            return
        }

        val deletedStep = currentSteps[position]
        if(deletedStep?.workflowStepId != 0) {
            Log.d(tag, "stepId not 0 need to delete from remote")
        }

        currentSteps.removeAt(position)
        workflowSteps.value = currentSteps
    }

    fun updateHtmlForEditableHtml(position: Int, filename: String) {
        val currentSteps = workflowSteps.value.orEmpty().toMutableList()
        val editableHtml = currentSteps[position].editableHtml
            ?: return
        editableHtml.fileName = filename
        workflowSteps.value = currentSteps
    }

}