package com.example.workflow_manager_frontend.data.repository

import android.util.Log
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Execution
import java.io.IOException


class ExecutionRepositoryImpl : ExecutionRepository{

    private val tag = "ExecutionRepository"
    override suspend fun getExecutions(workflowId: Int?): List<Execution>? {
        try{
            val response = RetrofitInstance.api.getExecutions(workflowId)
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
}