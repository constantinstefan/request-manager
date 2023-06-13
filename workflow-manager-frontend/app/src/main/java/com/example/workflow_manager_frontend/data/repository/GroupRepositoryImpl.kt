package com.example.workflow_manager_frontend.data.repository

import android.util.Log
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Customer
import com.example.workflow_manager_frontend.domain.Group
import java.io.IOException

class GroupRepositoryImpl : GroupRepository {
    private val tag = "GroupRepository"

    override suspend fun getGroupsByCustomerId(customerId: Int) : List<Group>? {
        try{
            val response = RetrofitInstance.api.getGroupsByCustomerId(customerId)
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
                return emptyList()
            }
            return response.body()
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
        return emptyList()
    }

    override suspend fun addCustomerToGroup(groupId: Int, customer: Customer) {
        try{
            val response = RetrofitInstance.api.addCustomerToGroup(groupId, customer)
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
            }
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
    }

    override suspend fun deleteCustomerFromGroup(groupId: Int, customerId: Int) {
        try{
            val response = RetrofitInstance.api.deleteCustomerFromGroup(groupId, customerId)
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
            }
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
    }

    override suspend fun getGroupById(groupId: Int): Group? {
        try{
            val response = RetrofitInstance.api.getGroupById(groupId)
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
                return null
            }
            return response.body()
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
        return null
    }

    override suspend fun getGroupMembers(groupId: Int): List<Customer>? {
        try{
            val response = RetrofitInstance.api.getCustomersByGroupId(groupId)
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
                return emptyList()
            }
            return response.body()
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
        return emptyList()
    }

    override suspend fun addGroup(group: Group)  : Group?{
        try{
            val response = RetrofitInstance.api.addGroup(group)
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
                return null
            }
            return response.body()
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
        return null
    }
}