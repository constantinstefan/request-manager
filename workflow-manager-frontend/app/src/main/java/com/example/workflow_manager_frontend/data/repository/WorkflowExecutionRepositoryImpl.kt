package com.example.workflow_manager_frontend.data.repository

import android.util.Log
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Variable
import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext
import com.example.workflow_manager_frontend.domain.WorkflowExecutionRequest
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class WorkflowExecutionRepositoryImpl : WorkflowExecutionRepository {

    private val tag = "WorkflowExecutionRepository"

    override suspend fun doExecution(workflowId: Long,
                                     workflowExecutionContext: WorkflowExecutionContext) {
        val variables: MutableList<Variable> = mutableListOf()
        workflowExecutionContext.variableMap.forEach { (key, value) ->
            if (value != null) {
                variables.add(Variable(key, value))
            }
        }

        val jsonDataRequestBody = RequestBody.create(
            MediaType.parse("application/json"),
            Gson().toJson(WorkflowExecutionRequest(variables)))

        val files : MutableList<MultipartBody.Part> = mutableListOf()
        workflowExecutionContext.fileMap.forEach{(key, value) ->
            val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), value)
            val extension = value.name.substringAfterLast('.', "")
            val dotExtension = if (extension.isNotBlank())  "." + extension else ""

            val filePart = MultipartBody.Part.createFormData("file", key + dotExtension , requestBody)
            files.add(filePart)
        }


        try {
            val response = RetrofitInstance.api.executeWorkflow(
                workflowId,
                jsonDataRequestBody,
                files
            )

            if (!response.isSuccessful) {
                Log.e(tag, response.toString())
                return
            }
            Log.d(tag, response.toString())
        } catch (e: Exception) {
            Log.e(tag, "Exception occurred: ${e.message}")
        }
    }

}