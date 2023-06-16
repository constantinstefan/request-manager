package com.example.workflow_manager_frontend.presentation.developer.home.execution

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.ExecutionRepository
import com.example.workflow_manager_frontend.data.source.network.NotificationClient
import com.example.workflow_manager_frontend.domain.Execution
import com.example.workflow_manager_frontend.domain.converter.ExecutionConverter
import com.example.workflow_manager_frontend.domain.converter.NotificationConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExecutionViewModel @Inject constructor(
    private val executionRepository: ExecutionRepository,
    private val notificationClient: NotificationClient
) : ViewModel() {
    val executions: MutableLiveData<MutableList<Execution>> = MutableLiveData()

    fun subscribeForExecutions(workflowId: Int?) {
        notificationClient.subscribe("/executions"){
            addExecution(it.payload)
        }
    }

    fun addExecution(execution: String) {
        val currentExecutions= executions.value.orEmpty().toMutableList()
        val newExecution = ExecutionConverter.fromString(execution) ?: return

        val index = currentExecutions.indexOfFirst { it.id == newExecution.id }
        val found = (index != -1)

        if(found) {
            currentExecutions[index] = newExecution
        }
        else {
            currentExecutions.add(0, newExecution)
        }
        executions.postValue(currentExecutions)
    }

    fun getExecutions(workflowId: Int?) : MutableLiveData<MutableList<Execution>> {
        viewModelScope.launch {
            executions.value = executionRepository.getExecutions(workflowId) as MutableList<Execution>?
        }
        return executions
    }


}