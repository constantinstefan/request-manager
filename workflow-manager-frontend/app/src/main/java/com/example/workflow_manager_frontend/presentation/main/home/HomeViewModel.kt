package com.example.workflow_manager_frontend.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.WorkflowRepository
import com.example.workflow_manager_frontend.domain.Workflow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    workflowRepository: WorkflowRepository
) : ViewModel()
{
    val state : MutableStateFlow<List<Workflow>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch {
            state.value = withContext(Dispatchers.IO) {
                workflowRepository.getWorkflows(true)
            }
            Log.d("viewmodel", state.value.toString())
        }
    }
}