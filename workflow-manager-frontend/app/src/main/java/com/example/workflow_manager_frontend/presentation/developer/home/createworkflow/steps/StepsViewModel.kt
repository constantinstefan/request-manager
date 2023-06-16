package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.data.repository.DeletedStepsRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowStepRepository
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.WorkflowStep
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(
    private val workflowStepRepository: WorkflowStepRepository,
    private val deletedStepsRepository: DeletedStepsRepository
): ViewModel() {

    private val tag = "StepsViewModel"

    private val workflow: MutableLiveData<Workflow?> = MutableLiveData()

    private val workflowSteps: MutableLiveData<MutableList<WorkflowStep>> = MutableLiveData()

    private var selectedPosition : Int = -1

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    fun getSelectedPosition() : Int {
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
        workflowStep.stepNumber = currentSteps.size + 1
        currentSteps.add(workflowStep)
        workflowSteps.value = currentSteps
    }

    fun getItem(position: Int): WorkflowStep? {
        return workflowSteps.value?.get(position)
    }

    suspend fun fetchFromRemote(workflow: Workflow) : List<WorkflowStep>?{
        if(workflow.id == null || workflow.id ==0) {
            return emptyList()
        }
        return workflowStepRepository.getWorkflowSteps(workflow.id, true)
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
            deletedStepsRepository.addDeletedStepToCache(deletedStep)
        }

        currentSteps.removeAt(position)
        workflowSteps.value = currentSteps
    }

    fun updateHtmlForEditableHtml(position: Int, filename: String, file: File) {
        val currentSteps = workflowSteps.value.orEmpty().toMutableList()
        val editableHtml = currentSteps[position].editableHtml
            ?: return
        editableHtml.fileName = filename
        editableHtml.file = file
        workflowSteps.value = currentSteps
    }

    fun setDocumentDescription(position: Int, description: String) {
        workflowSteps.value?.get(position)?.document?.description = description
    }

    fun setDocumentUploadFileContentVariable(position: Int, fileContentVariable: String) {
        workflowSteps.value?.get(position)?.document?.uploadedFileVariable = fileContentVariable
    }

    fun setIsRequiredForDocument(position: Int, isRequired: Boolean) {
        workflowSteps.value?.get(position)?.document?.isRequired = isRequired
    }

    fun setHtmlFileVariableForEditableHtml(position: Int, htmlFileVariable: String) {
        workflowSteps.value?.get(position)?.editableHtml?.uploadedEditedHtmlFileVariable = htmlFileVariable
    }

    fun setPdfFileVariableForEditableHtml(position: Int, pdfFileVariable: String) {
        workflowSteps.value?.get(position)?.editableHtml?.pdfResultVariable = pdfFileVariable
    }

    fun setIsRequiredForEditableHtml(position: Int, isRequired: Boolean) {
        workflowSteps.value?.get(position)?.editableHtml?.isRequired = isRequired
    }

    fun setReceiverForEmail(position: Int, receiver: String) {
        workflowSteps.value?.get(position)?.email?.receiverEmail = receiver
    }

    fun setContentForEmail(position: Int, content: String) {
        workflowSteps.value?.get(position)?.email?.content = content
    }

    fun setSubjectForEmail(position: Int, subject: String) {
        workflowSteps.value?.get(position)?.email?.subject = subject
    }

    fun setAttachmentsForEmail(position: Int, attachments: String) {
        workflowSteps.value?.get(position)?.email?.attachments = attachments
    }

    fun setNameForFormField(position: Int, formFieldPosition: Int, name: String) {
        workflowSteps.value?.get(position)?.formFields?.get(formFieldPosition)?.name = name
    }
    fun setLabelForFormField(position: Int, formFieldPosition: Int, label: String) {
        workflowSteps.value?.get(position)?.formFields?.get(formFieldPosition)?.label = label
    }

    fun setIsRequiredForFormField(position: Int, formFieldPosition: Int, isRequired: Boolean) {
        workflowSteps.value?.get(position)?.formFields?.get(formFieldPosition)?.isRequired = isRequired
    }

    fun setOutputVariableForChatGptStep(position: Int, outputVariable: String) {
        workflowSteps.value?.get(position)?.chatGptStep?.outputVariable = outputVariable;
    }

    fun setPromptForChatGptStep(position: Int, prompt: String) {
        workflowSteps.value?.get(position)?.chatGptStep?.prompt = prompt;
    }
}