package com.example.workflow_manager_frontend.presentation.developer.groups.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.GroupRepository
import com.example.workflow_manager_frontend.data.repository.UserRepository
import com.example.workflow_manager_frontend.domain.Customer
import com.example.workflow_manager_frontend.domain.Group
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val members: MutableLiveData<MutableList<Customer>> = MutableLiveData()
    private val users: MutableLiveData<MutableList<Customer>> = MutableLiveData()

    private lateinit var group: Group

    val selectedMember : MutableLiveData<Customer> = MutableLiveData()

    init {
        viewModelScope.launch {
            users.value = userRepository.getUsers() as MutableList<Customer>?
        }
    }

    suspend fun setGroup(group: Group) {
        members.value = groupRepository.getGroupMembers(group.id) as MutableList<Customer>?
        this.group = group
    }

    fun getMembers(groupId : Int) : MutableLiveData<MutableList<Customer>>{
        return members
    }

    fun getUsers() : MutableLiveData<MutableList<Customer>> {
        return users
    }

    suspend fun addMember(groupId: Int, member: Customer?) {
        if(member == null) return
        groupRepository.addCustomerToGroup(groupId, member)
        val currentList : MutableList<Customer> = members.value.orEmpty().toMutableList()
        currentList.add(member)
        members.value = currentList
    }

    suspend fun removeMember(groupId: Int, member: Customer?) {
        if(member == null) return
        groupRepository.deleteCustomerFromGroup(groupId, member.id)
        val currentList:  MutableList<Customer> = members.value.orEmpty().toMutableList()
        currentList.remove(member)
        members.value = currentList
    }
}