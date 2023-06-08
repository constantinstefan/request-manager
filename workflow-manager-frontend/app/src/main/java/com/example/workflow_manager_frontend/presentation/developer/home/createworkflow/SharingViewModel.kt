package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.GroupRepository
import com.example.workflow_manager_frontend.data.repository.UserRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowRepository
import com.example.workflow_manager_frontend.domain.Group
import com.example.workflow_manager_frontend.domain.Sharing
import com.example.workflow_manager_frontend.domain.Workflow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharingViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val sharing: MutableLiveData<Sharing?> = MutableLiveData()

    private val groups: MutableLiveData<List<Group>> = MutableLiveData()

    var selectedGroup : Group? = null

    init {
        viewModelScope.launch {
            val principal = userRepository.getPrincipal() ?: return@launch
            groups.value = groupRepository.getGroupsByCustomerId(principal.id)
        }
    }
    fun setType(sharingType: String) {
        val currentSharing: Sharing = sharing.value ?: Sharing()
        currentSharing.sharingType = sharingType
        sharing.value = currentSharing
    }

    fun getType() : String? {
        return sharing.value?.sharingType
    }

    fun getGroups(): MutableLiveData<List<Group>> {
        return groups
    }
}