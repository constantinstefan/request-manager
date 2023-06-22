package com.example.workflow_manager_frontend.presentation.developer.groups.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.GroupRepository
import com.example.workflow_manager_frontend.data.repository.UserRepository
import com.example.workflow_manager_frontend.domain.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val groupName = MutableLiveData<String?>()
    val groupDescription = MutableLiveData<String?>()
    val success = MutableLiveData(false)

    suspend fun createGroup(name: String, description: String) : Boolean {
        val group = groupRepository.addGroup(Group(name = name, description = description))
            ?: return false
        val principal = userRepository.getPrincipal()
            ?: return false
        groupRepository.addCustomerToGroup(group.id, principal)
        return true
    }

    fun onCreateGroup() {
        viewModelScope.launch {
            if(groupName.value == null || groupDescription.value == null)
                return@launch
            if(createGroup(groupName.value!!, groupDescription.value!!)) {
                success.postValue(true)
            }
        }
    }
}