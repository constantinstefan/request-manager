package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.data.repository.DeletedStepsRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowStepRepository
import com.example.workflow_manager_frontend.domain.request.SharingRequest
import com.example.workflow_manager_frontend.domain.request.WorkflowRequest
import com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps.StepsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateWorkflowViewModel @Inject constructor(
    private val workflowRepository: WorkflowRepository,
    private val workflowStepRepository: WorkflowStepRepository,
   // private val deletedStepsRepository: DeletedStepsRepository
): ViewModel() {

    private lateinit var sharingViewModel: SharingViewModel
    private lateinit var stepsViewModel: StepsViewModel

    fun setSharingViewModel(sharingViewModel: SharingViewModel) {
        this.sharingViewModel = sharingViewModel
    }
    fun setStepsViewModel(stepsViewModel: StepsViewModel) {
        this.stepsViewModel = stepsViewModel
    }

    suspend fun createWorkflow() : Boolean {
        val name = stepsViewModel.getWorkflow().value?.name.orEmpty()
        val description = stepsViewModel.getWorkflow().value?.description.orEmpty()

        val workflow = workflowRepository.setWorkflowRemote(WorkflowRequest(
            name = name,
            description =  description)
        ) ?: return false

        val sharingType = sharingViewModel.getType() ?: "PUBLIC"
        var groupId = when(sharingType){
            "GROUP" -> sharingViewModel.selectedGroup?.id
            else -> null
        }
        workflowRepository.setSharingRemote(workflow.id, SharingRequest(
            sharingType = sharingType,
            groupId = groupId)
        ) ?: return false

        stepsViewModel.getWorkflowSteps().value?.forEach {step ->
            workflowStepRepository.createWorkflowStep(workflow.id, step)
        }
        return true
    }

    suspend fun updateWorkflow(workflowId: Int?) : Boolean {
        TODO()
    }
}