package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.ExecutionRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowStepRepository
import com.example.workflow_manager_frontend.data.source.network.NotificationClient
import com.example.workflow_manager_frontend.domain.Execution
import com.example.workflow_manager_frontend.domain.StepExecution
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.example.workflow_manager_frontend.domain.converter.ExecutionConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExecutionDetailsViewModel @Inject constructor(
    private val workflowStepRepository: WorkflowStepRepository
) : ViewModel() {

    private val stepExecutions: MutableLiveData<MutableList<StepExecution>> = MutableLiveData()

    private val staticExecution : MutableLiveData<Execution> = MutableLiveData()

    fun subscribeForExecutions(workflowId: Int?) {
        val notificationClient = NotificationClient()
        notificationClient.subscribe("/executions") {
            updateExecution(it.payload)
        }
    }

    fun getStepExecutions(execution: Execution) : MutableLiveData<MutableList<StepExecution>> {
        viewModelScope.launch {
            val steps = workflowStepRepository.getWorkflowSteps(execution.workflowId, true) as MutableList<WorkflowStep>?
            if (steps != null) {
                stepExecutions.value = steps.map{
                    StepExecution(step=it, execution = execution)
                } as MutableList<StepExecution>
            }
            staticExecution.postValue(execution)
        }
        return stepExecutions
    }

    fun getStaticExecution(): MutableLiveData<Execution> {
        return staticExecution
    }

    fun updateExecution(execution: String) {
        val newExecution = ExecutionConverter.fromString(execution) ?: return
        val currentStepExecutions= stepExecutions.value.orEmpty().toMutableList()

        currentStepExecutions.forEachIndexed { index, stepExecution ->
            currentStepExecutions[index] = stepExecution.copy(execution = newExecution)
        }
        stepExecutions.postValue(currentStepExecutions)
    }


}