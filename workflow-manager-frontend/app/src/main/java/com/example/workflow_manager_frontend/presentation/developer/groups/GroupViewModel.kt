package com.example.workflow_manager_frontend.presentation.developer.groups

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
class GroupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
    ) : ViewModel() {

    private val result: MutableLiveData<List<Group>> = MutableLiveData()
    private lateinit var groups: List<Group>

    init {
        viewModelScope.launch {
            val principal = userRepository.getPrincipal() ?: return@launch
            groups = groupRepository.getGroupsByCustomerId(principal.id)!!
            result.value = groups
        }
    }

    fun filter(query: String?) {
        if(query.isNullOrEmpty()) {
            result.value = groups
            return
        }
        result.value = groups.filter {
            it.name.startsWith(query, true)
        }
    }

    fun getGroups(): MutableLiveData<List<Group>> {
        return result;
    }
}