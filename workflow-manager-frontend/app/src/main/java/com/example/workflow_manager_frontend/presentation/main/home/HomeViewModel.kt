package com.example.workflow_manager_frontend.presentation.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.GroupRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowRepository
import com.example.workflow_manager_frontend.data.source.network.NotificationClient
import com.example.workflow_manager_frontend.domain.Workflow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    workflowRepository: WorkflowRepository,
    groupRepository: GroupRepository,
    notificationClient: NotificationClient
) : ViewModel()
{
    val state : MutableLiveData<List<Workflow>> = MutableLiveData()
    private lateinit var workflows: List<Workflow>

    init {
        notificationClient.subscribe()
        viewModelScope.launch {
            workflows = workflowRepository.getWorkflows(true)
            workflows.forEach { workflow ->
                workflow.sharing?.group = workflow.sharing?.groupId?.let {
                    groupRepository.getGroupById(it)
                }
            }
            state.value = workflows
        }
    }

    fun filter(query: String?) {
        if(query.isNullOrEmpty()) {
            state.value = workflows
            return
        }
        state.value = workflows.filter {
            it.name.startsWith(query, true)
        }
    }
}