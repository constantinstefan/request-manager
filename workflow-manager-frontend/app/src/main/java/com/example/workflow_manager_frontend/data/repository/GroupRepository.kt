package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Customer
import com.example.workflow_manager_frontend.domain.Group

interface GroupRepository {
    suspend fun getGroupsByCustomerId(customerId : Int) : List<Group>?

    suspend fun getGroupById(groupId: Int) : Group?

    suspend fun addCustomerToGroup(groupId: Int, customer: Customer)

    suspend fun deleteCustomerFromGroup(groupId: Int, customerId: Int)
}